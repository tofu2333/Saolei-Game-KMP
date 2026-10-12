package com.exp.saolei.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exp.saolei.audio.SoundType
import com.exp.saolei.engine.BoardState
import com.exp.saolei.engine.MinesweeperEngine
import com.exp.saolei.getCurrentDateString
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.GameStatus
import com.exp.saolei.model.VictoryDialogStyle
import com.exp.saolei.storage.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel(
    initialSettings: GameSettings = DataManager.loadSettings(),
    private val engine: MinesweeperEngine = MinesweeperEngine(initialSettings),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : ViewModel() {

    private val _uiState: MutableStateFlow<GameUiState>
    val uiState: StateFlow<GameUiState>

    private val _effect = Channel<GameEffect>(Channel.BUFFERED)
    val effect: Flow<GameEffect> = _effect.receiveAsFlow()

    private var timerJob: Job? = null

    init {
        val initialDiff = Difficulty.EASY
        val initialBoard = engine.createNewBoard(initialDiff)
        _uiState = MutableStateFlow(
            GameUiState(
                difficulty = initialDiff,
                boardState = initialBoard,
                interactionMode = initialSettings.defaultInteractionMode,
                settings = initialSettings
            )
        )
        uiState = _uiState.asStateFlow()
    }

    fun processIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.CellClick -> handleCellClick(intent.row, intent.col)
            is GameIntent.CellLongClick -> handleCellLongClick(intent.row, intent.col)
            is GameIntent.CellRightClick -> handleCellRightClick(intent.row, intent.col)
            is GameIntent.ChordClick -> handleChordClick(intent.row, intent.col)
            is GameIntent.ToggleInteractionMode -> handleToggleInteractionMode(intent.mode)
            is GameIntent.RestartGame -> handleRestartGame(intent.difficulty)
            is GameIntent.ChangeDifficulty -> handleChangeDifficulty(intent.difficulty)
            is GameIntent.UpdateZoomPan -> handleUpdateZoomPan(intent.zoomChange, intent.panChange)
            is GameIntent.ZoomIn -> handleZoomIn()
            is GameIntent.ZoomOut -> handleZoomOut()
            is GameIntent.ResetView -> handleResetView()
            is GameIntent.SetFitScale -> handleSetFitScale(intent.fitScale)
            is GameIntent.UpdateSettings -> handleUpdateSettings(intent.settings)
            is GameIntent.SetCustomDialogVisible -> _uiState.update { it.copy(showCustomDialog = intent.visible) }
            is GameIntent.DismissVictoryDialog -> _uiState.update { it.copy(activeVictoryStyle = null) }
            is GameIntent.DismissDefeatDialog -> _uiState.update { it.copy(showDefeatDialog = false) }
            is GameIntent.PreviewVictoryStyle -> _uiState.update { it.copy(activeVictoryStyle = intent.style) }
            is GameIntent.TickTimer -> handleTickTimer()
        }
    }

    private fun handleCellClick(row: Int, col: Int) {
        val currentState = _uiState.value
        val currentBoard = currentState.boardState
        if (currentBoard.status == GameStatus.WON || currentBoard.status == GameStatus.LOST) return

        val cell = currentBoard[row, col] ?: return

        if (cell.isRevealed) {
            if (cell.adjacentMines > 0) {
                handleChordClick(row, col)
            }
            return
        }

        if (currentState.interactionMode == GameInteractionMode.DIG) {
            revealCell(row, col)
        } else {
            toggleFlag(row, col)
        }
    }

    private fun handleCellLongClick(row: Int, col: Int) {
        val currentState = _uiState.value
        val currentBoard = currentState.boardState
        if (currentBoard.status == GameStatus.WON || currentBoard.status == GameStatus.LOST) return

        val cell = currentBoard[row, col] ?: return
        if (cell.isRevealed) return

        // Long press triggers the opposite of current mode
        if (currentState.interactionMode == GameInteractionMode.DIG) {
            toggleFlag(row, col)
        } else {
            revealCell(row, col)
        }
    }

    private fun handleCellRightClick(row: Int, col: Int) {
        val currentState = _uiState.value
        val currentBoard = currentState.boardState
        if (currentBoard.status == GameStatus.WON || currentBoard.status == GameStatus.LOST) return

        val cell = currentBoard[row, col] ?: return
        if (cell.isRevealed) {
            if (cell.adjacentMines > 0) {
                handleChordClick(row, col)
            }
            return
        }

        // Right-clicking an unrevealed cell always toggles flag
        toggleFlag(row, col)
    }

    private fun revealCell(row: Int, col: Int) {
        val currentState = _uiState.value
        val newBoard = engine.reveal(currentState.boardState, row, col)
        if (newBoard != currentState.boardState) {
            sendEffect(GameEffect.TriggerHaptic)
            val isStatusChanged = newBoard.status != currentState.boardState.status

            _uiState.update { it.copy(boardState = newBoard) }

            if (newBoard.status == GameStatus.PLAYING && timerJob == null) {
                startTimer()
            }

            when (newBoard.status) {
                GameStatus.WON -> onGameWon(newBoard)
                GameStatus.LOST -> onGameLost(newBoard)
                else -> sendEffect(GameEffect.PlaySound(SoundType.DIG))
            }
        }
    }

    private fun toggleFlag(row: Int, col: Int) {
        val currentState = _uiState.value
        val newBoard = engine.toggleFlag(currentState.boardState, row, col)
        if (newBoard != currentState.boardState) {
            sendEffect(GameEffect.TriggerHaptic)
            val nowFlagged = newBoard[row, col]?.visualState == CellVisualState.FLAGGED
            if (nowFlagged) {
                sendEffect(GameEffect.PlaySound(SoundType.FLAG))
            } else {
                sendEffect(GameEffect.PlaySound(SoundType.UNFLAG))
            }
            _uiState.update { it.copy(boardState = newBoard) }
        }
    }

    private fun handleChordClick(row: Int, col: Int) {
        val currentState = _uiState.value
        val newBoard = engine.chord(currentState.boardState, row, col)
        if (newBoard != currentState.boardState) {
            sendEffect(GameEffect.TriggerHaptic)
            _uiState.update { it.copy(boardState = newBoard) }

            when (newBoard.status) {
                GameStatus.WON -> onGameWon(newBoard)
                GameStatus.LOST -> onGameLost(newBoard)
                else -> sendEffect(GameEffect.PlaySound(SoundType.CHORD))
            }
        }
    }

    private fun handleToggleInteractionMode(mode: GameInteractionMode) {
        _uiState.update { it.copy(interactionMode = mode) }
        sendEffect(GameEffect.PlaySound(SoundType.CLICK))
    }

    private fun handleRestartGame(diff: Difficulty? = null) {
        stopTimer()
        val targetDiff = diff ?: _uiState.value.difficulty
        val newBoard = engine.createNewBoard(targetDiff)

        _uiState.update {
            it.copy(
                difficulty = targetDiff,
                boardState = newBoard,
                elapsedSeconds = 0,
                exactElapsedSeconds = 0.0,
                scale = it.fitScale,
                offset = Offset.Zero,
                activeVictoryStyle = null,
                showDefeatDialog = false,
                isNewRecord = false,
                userRank = null
            )
        }
        sendEffect(GameEffect.PlaySound(SoundType.CLICK))
    }

    private fun handleChangeDifficulty(diff: Difficulty) {
        handleRestartGame(diff)
    }

    private fun handleSetFitScale(fitScale: Float) {
        _uiState.update { current ->
            val wasAtFit = kotlin.math.abs(current.scale - current.fitScale) < 0.02f || (current.scale == 1f && current.fitScale == 1f)
            val updated = current.copy(fitScale = fitScale)
            if (current.offset == Offset.Zero && wasAtFit) {
                updated.copy(scale = fitScale)
            } else {
                updated
            }
        }
    }

    private fun handleUpdateZoomPan(zoomChange: Float, panChange: Offset) {
        _uiState.update {
            val minScale = (it.fitScale * 0.5f).coerceIn(0.12f, 0.5f)
            val newScale = (it.scale * zoomChange).coerceIn(minScale, 4.0f)
            val newOffset = Offset(it.offset.x + panChange.x, it.offset.y + panChange.y)
            it.copy(scale = newScale, offset = newOffset)
        }
    }

    private fun handleZoomIn() {
        _uiState.update {
            val minScale = (it.fitScale * 0.5f).coerceIn(0.12f, 0.5f)
            val newScale = (it.scale * 1.25f).coerceIn(minScale, 4.0f)
            it.copy(scale = newScale)
        }
        sendEffect(GameEffect.PlaySound(SoundType.CLICK))
    }

    private fun handleZoomOut() {
        _uiState.update {
            val minScale = (it.fitScale * 0.5f).coerceIn(0.12f, 0.5f)
            val newScale = (it.scale / 1.25f).coerceIn(minScale, 4.0f)
            it.copy(scale = newScale)
        }
        sendEffect(GameEffect.PlaySound(SoundType.CLICK))
    }

    private fun handleResetView() {
        _uiState.update { current ->
            val targetScale = if (current.fitScale < 1f) {
                if (kotlin.math.abs(current.scale - current.fitScale) < 0.02f) 1.0f else current.fitScale
            } else {
                1.0f
            }
            current.copy(scale = targetScale, offset = Offset.Zero)
        }
        sendEffect(GameEffect.PlaySound(SoundType.CLICK))
    }

    private fun handleUpdateSettings(newSettings: GameSettings) {
        engine.updateSettings(newSettings)
        DataManager.saveSettings(newSettings)
        _uiState.update { it.copy(settings = newSettings) }
    }

    private fun handleTickTimer() {
        _uiState.update {
            val newExact = it.exactElapsedSeconds + 0.1
            it.copy(
                exactElapsedSeconds = newExact,
                elapsedSeconds = newExact.toInt()
            )
        }
    }

    private fun startTimer() {
        stopTimer()
        timerJob = coroutineScope.launch {
            while (isActive && _uiState.value.boardState.status == GameStatus.PLAYING) {
                delay(100)
                handleTickTimer()
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun onGameWon(board: BoardState) {
        stopTimer()
        sendEffect(GameEffect.PlaySound(SoundType.WIN))
        sendEffect(GameEffect.TriggerHaptic)

        val state = _uiState.value
        val result = DataManager.recordWin(
            difficultyType = state.difficulty.type,
            difficultyName = state.difficulty.name,
            timeSeconds = state.exactElapsedSeconds,
            clicks = board.clicksCount,
            flagsPlaced = board.flaggedCount,
            dateFormatted = getCurrentDateString()
        )

        _uiState.update {
            it.copy(
                boardState = board,
                isNewRecord = result.first,
                userRank = result.second,
                activeVictoryStyle = VictoryDialogStyle.random()
            )
        }
    }

    private fun onGameLost(board: BoardState) {
        stopTimer()
        sendEffect(GameEffect.PlaySound(SoundType.EXPLODE))
        sendEffect(GameEffect.TriggerHaptic)
        _uiState.update {
            it.copy(
                boardState = board,
                showDefeatDialog = true
            )
        }
    }

    private fun sendEffect(effect: GameEffect) {
        _effect.trySend(effect)
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        coroutineScope.cancel()
    }
}

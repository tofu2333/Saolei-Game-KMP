package com.exp.saolei.presentation

import androidx.compose.ui.geometry.Offset
import com.exp.saolei.audio.SoundType
import com.exp.saolei.engine.BoardState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.VictoryDialogStyle

data class GameUiState(
    val difficulty: Difficulty = Difficulty.EASY,
    val boardState: BoardState,
    val interactionMode: GameInteractionMode = GameInteractionMode.DIG,
    val elapsedSeconds: Int = 0,
    val exactElapsedSeconds: Double = 0.0,
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
    val fitScale: Float = 1f,
    val settings: GameSettings = GameSettings(),
    val activeVictoryStyle: VictoryDialogStyle? = null,
    val showDefeatDialog: Boolean = false,
    val showCustomDialog: Boolean = false,
    val isNewRecord: Boolean = false,
    val userRank: Int? = null
)

sealed interface GameIntent {
    data class CellClick(val row: Int, val col: Int) : GameIntent
    data class CellLongClick(val row: Int, val col: Int) : GameIntent
    data class CellRightClick(val row: Int, val col: Int) : GameIntent
    data class ChordClick(val row: Int, val col: Int) : GameIntent
    data class ToggleInteractionMode(val mode: GameInteractionMode) : GameIntent
    data class RestartGame(val difficulty: Difficulty? = null) : GameIntent
    data class ChangeDifficulty(val difficulty: Difficulty) : GameIntent
    data class UpdateZoomPan(val zoomChange: Float, val panChange: Offset) : GameIntent
    data object ZoomIn : GameIntent
    data object ZoomOut : GameIntent
    data object ResetView : GameIntent
    data class SetFitScale(val fitScale: Float) : GameIntent
    data class UpdateSettings(val settings: GameSettings) : GameIntent
    data class SetCustomDialogVisible(val visible: Boolean) : GameIntent
    data object DismissVictoryDialog : GameIntent
    data object DismissDefeatDialog : GameIntent
    data class PreviewVictoryStyle(val style: VictoryDialogStyle?) : GameIntent
    data object TickTimer : GameIntent
}

sealed interface GameEffect {
    data class PlaySound(val sound: SoundType) : GameEffect
    data object TriggerHaptic : GameEffect
}

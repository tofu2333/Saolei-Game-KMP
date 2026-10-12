package com.exp.saolei

import androidx.compose.ui.geometry.Offset
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.GameStatus
import com.exp.saolei.presentation.GameIntent
import com.exp.saolei.presentation.GameViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GameViewModelTest {

    @Test
    fun testInitialState() {
        val viewModel = GameViewModel()
        val state = viewModel.uiState.value

        assertEquals(Difficulty.EASY, state.difficulty)
        assertEquals(GameStatus.IDLE, state.boardState.status)
        assertEquals(GameInteractionMode.DIG, state.interactionMode)
        assertEquals(0, state.elapsedSeconds)
        assertEquals(1f, state.scale)
        assertEquals(Offset.Zero, state.offset)
        assertFalse(state.showDefeatDialog)
    }

    @Test
    fun testCellClickInDigModeRevealsAndStartsGame() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.CellClick(4, 4))

        val state = viewModel.uiState.value
        assertEquals(GameStatus.PLAYING, state.boardState.status)

        val centerCell = state.boardState[4, 4]
        assertNotNull(centerCell)
        assertTrue(centerCell.isRevealed)
        assertFalse(centerCell.isMine)
    }

    @Test
    fun testToggleInteractionModeAndFlagging() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.ToggleInteractionMode(GameInteractionMode.FLAG))

        assertEquals(GameInteractionMode.FLAG, viewModel.uiState.value.interactionMode)

        // In Flag mode, normal tap toggles flag
        viewModel.processIntent(GameIntent.CellClick(0, 0))

        val state = viewModel.uiState.value
        assertEquals(CellVisualState.FLAGGED, state.boardState[0, 0]?.visualState)
        assertEquals(1, state.boardState.flaggedCount)
    }

    @Test
    fun testCellLongClickTogglesFlagInDigMode() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.CellLongClick(1, 1))

        val state = viewModel.uiState.value
        assertEquals(CellVisualState.FLAGGED, state.boardState[1, 1]?.visualState)
        assertEquals(1, state.boardState.flaggedCount)
    }

    @Test
    fun testCellLongClickRevealsInFlagMode() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.ToggleInteractionMode(GameInteractionMode.FLAG))
        assertEquals(GameInteractionMode.FLAG, viewModel.uiState.value.interactionMode)

        // In Flag mode, long click performs quick dig
        viewModel.processIntent(GameIntent.CellLongClick(4, 4))
        val state = viewModel.uiState.value
        assertEquals(GameStatus.PLAYING, state.boardState.status)
        val centerCell = state.boardState[4, 4]
        assertNotNull(centerCell)
        assertTrue(centerCell.isRevealed)
    }

    @Test
    fun testChangeDifficultyAndRestart() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.ChangeDifficulty(Difficulty.MEDIUM))

        val state = viewModel.uiState.value
        assertEquals(Difficulty.MEDIUM, state.difficulty)
        assertEquals(16, state.boardState.difficulty.rows)
        assertEquals(16, state.boardState.difficulty.cols)
        assertEquals(40, state.boardState.difficulty.mines)
        assertEquals(GameStatus.IDLE, state.boardState.status)

        // Make move
        viewModel.processIntent(GameIntent.CellClick(2, 2))
        assertEquals(GameStatus.PLAYING, viewModel.uiState.value.boardState.status)

        // Restart
        viewModel.processIntent(GameIntent.RestartGame())
        val resetState = viewModel.uiState.value
        assertEquals(GameStatus.IDLE, resetState.boardState.status)
        assertEquals(0, resetState.elapsedSeconds)
    }

    @Test
    fun testZoomPanAndResetView() {
        val viewModel = GameViewModel()
        viewModel.processIntent(GameIntent.UpdateZoomPan(1.5f, Offset(20f, 30f)))

        val zoomedState = viewModel.uiState.value
        assertEquals(1.5f, zoomedState.scale)
        assertEquals(Offset(20f, 30f), zoomedState.offset)

        viewModel.processIntent(GameIntent.ResetView)
        val resetState = viewModel.uiState.value
        assertEquals(1f, resetState.scale)
        assertEquals(Offset.Zero, resetState.offset)
    }

    @Test
    fun testUpdateSettings() {
        val viewModel = GameViewModel()
        val newSettings = GameSettings(
            firstClickSafe = true,
            sfxVolume = 0.5f,
            bgmVolume = 0.2f
        )
        viewModel.processIntent(GameIntent.UpdateSettings(newSettings))

        assertEquals(newSettings, viewModel.uiState.value.settings)
    }

    @Test
    fun testZoomInAndZoomOut() {
        val viewModel = GameViewModel()
        assertEquals(1f, viewModel.uiState.value.scale)

        viewModel.processIntent(GameIntent.ZoomIn)
        assertEquals(1.25f, viewModel.uiState.value.scale)

        viewModel.processIntent(GameIntent.ZoomOut)
        assertEquals(1.0f, viewModel.uiState.value.scale)
    }

    @Test
    fun testSetFitScaleAndAutoFit() {
        val viewModel = GameViewModel()
        assertEquals(1f, viewModel.uiState.value.scale)
        assertEquals(1f, viewModel.uiState.value.fitScale)

        // When fit scale is reported (e.g. 0.65f for Expert 16x30)
        viewModel.processIntent(GameIntent.SetFitScale(0.65f))
        val state = viewModel.uiState.value
        assertEquals(0.65f, state.fitScale)
        // Since initial scale was at default and no offset, scale automatically adapted to fit the board
        assertEquals(0.65f, state.scale)

        // Clicking reset toggles between 1.0f (100%) and fitScale (0.65f)
        viewModel.processIntent(GameIntent.ResetView)
        assertEquals(1.0f, viewModel.uiState.value.scale)

        viewModel.processIntent(GameIntent.ResetView)
        assertEquals(0.65f, viewModel.uiState.value.scale)
    }
}

package com.exp.saolei

import com.exp.saolei.engine.MinesweeperEngine
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.GameStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MinesweeperEngineTest {

    @Test
    fun testBoardInitialization() {
        val engine = MinesweeperEngine()
        val board = engine.createNewBoard(Difficulty.EASY)

        assertEquals(9, board.difficulty.rows)
        assertEquals(9, board.difficulty.cols)
        assertEquals(10, board.difficulty.mines)
        assertEquals(GameStatus.IDLE, board.status)
        assertEquals(0, board.flaggedCount)
        assertEquals(0, board.revealedCount)
    }

    @Test
    fun testFirstClickSafeAndRevealed() {
        val engine = MinesweeperEngine(GameSettings(firstClickSafe = true, firstClickEmpty3x3 = true))
        val board = engine.createNewBoard(Difficulty.EASY)

        val afterFirstClick = engine.reveal(board, 4, 4)
        assertEquals(GameStatus.PLAYING, afterFirstClick.status)

        val centerCell = afterFirstClick[4, 4]
        assertNotNull(centerCell)
        assertTrue(centerCell.isRevealed)
        assertFalse(centerCell.isMine)
        assertEquals(0, centerCell.adjacentMines)
    }

    @Test
    fun testFlaggingAndQuestioning() {
        val engine = MinesweeperEngine(GameSettings(questionMarkEnabled = true))
        val board = engine.createNewBoard(Difficulty.EASY)

        val flagged = engine.toggleFlag(board, 0, 0)
        assertEquals(CellVisualState.FLAGGED, flagged[0, 0]?.visualState)
        assertEquals(1, flagged.flaggedCount)

        val questioned = engine.toggleFlag(flagged, 0, 0)
        assertEquals(CellVisualState.QUESTIONED, questioned[0, 0]?.visualState)
        assertEquals(0, questioned.flaggedCount)

        val hiddenAgain = engine.toggleFlag(questioned, 0, 0)
        assertEquals(CellVisualState.HIDDEN, hiddenAgain[0, 0]?.visualState)
        assertEquals(0, hiddenAgain.flaggedCount)
    }

    @Test
    fun testGameOverCorrectAndFalseFlags() {
        val engine = MinesweeperEngine(GameSettings(firstClickSafe = false))
        val initialBoard = engine.createNewBoard(Difficulty.EASY)

        val customGrid = initialBoard.grid.map { row ->
            row.map { cell ->
                when {
                    cell.row == 0 && cell.col == 0 -> cell.copy(isMine = true, visualState = CellVisualState.FLAGGED)
                    cell.row == 0 && cell.col == 1 -> cell.copy(isMine = false, visualState = CellVisualState.FLAGGED)
                    cell.row == 1 && cell.col == 0 -> cell.copy(isMine = true, visualState = CellVisualState.HIDDEN)
                    cell.row == 1 && cell.col == 1 -> cell.copy(isMine = true, visualState = CellVisualState.HIDDEN)
                    else -> cell.copy(isMine = false, visualState = CellVisualState.HIDDEN)
                }
            }
        }
        val board = initialBoard.copy(grid = customGrid, status = GameStatus.PLAYING)
        val lostBoard = engine.reveal(board, 1, 0)

        assertEquals(GameStatus.LOST, lostBoard.status)

        val triggeredMine = lostBoard[1, 0]
        assertNotNull(triggeredMine)
        assertTrue(triggeredMine.isRevealed)
        assertTrue(triggeredMine.isTriggeredMine)

        val correctFlag = lostBoard[0, 0]
        assertNotNull(correctFlag)
        assertTrue(correctFlag.isRevealed)
        assertTrue(correctFlag.isCorrectFlag)
        assertFalse(correctFlag.isFalseFlag)

        val falseFlag = lostBoard[0, 1]
        assertNotNull(falseFlag)
        assertTrue(falseFlag.isRevealed)
        assertTrue(falseFlag.isFalseFlag)
        assertFalse(falseFlag.isCorrectFlag)

        val unflaggedMine = lostBoard[1, 1]
        assertNotNull(unflaggedMine)
        assertTrue(unflaggedMine.isRevealed)
        assertFalse(unflaggedMine.isTriggeredMine)
        assertFalse(unflaggedMine.isCorrectFlag)
    }
}


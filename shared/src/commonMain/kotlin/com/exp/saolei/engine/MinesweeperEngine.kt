package com.exp.saolei.engine

import com.exp.saolei.model.Cell
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.GameStatus
import kotlin.random.Random

data class BoardState(
    val difficulty: Difficulty,
    val grid: List<List<Cell>>,
    val status: GameStatus = GameStatus.IDLE,
    val flaggedCount: Int = 0,
    val revealedCount: Int = 0,
    val clicksCount: Int = 0,
    val triggeredCell: Pair<Int, Int>? = null
) {
    val remainingMines: Int get() = difficulty.mines - flaggedCount
    val totalNonMines: Int get() = difficulty.totalCells - difficulty.mines

    operator fun get(row: Int, col: Int): Cell? {
        if (row !in 0 until difficulty.rows || col !in 0 until difficulty.cols) return null
        return grid[row][col]
    }
}

class MinesweeperEngine(
    private var settings: GameSettings = GameSettings()
) {
    fun updateSettings(newSettings: GameSettings) {
        settings = newSettings
    }

    fun createNewBoard(difficulty: Difficulty): BoardState {
        val grid = List(difficulty.rows) { r ->
            List(difficulty.cols) { c ->
                Cell(row = r, col = c)
            }
        }
        return BoardState(
            difficulty = difficulty,
            grid = grid,
            status = GameStatus.IDLE
        )
    }

    private fun getNeighbors(row: Int, col: Int, rows: Int, cols: Int): List<Pair<Int, Int>> {
        val neighbors = mutableListOf<Pair<Int, Int>>()
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val nr = row + dr
                val nc = col + dc
                if (nr in 0 until rows && nc in 0 until cols) {
                    neighbors.add(Pair(nr, nc))
                }
            }
        }
        return neighbors
    }

    private fun placeMines(
        difficulty: Difficulty,
        firstRow: Int,
        firstCol: Int
    ): List<List<Cell>> {
        val rows = difficulty.rows
        val cols = difficulty.cols
        val totalMines = difficulty.mines

        val excluded = mutableSetOf<Pair<Int, Int>>()
        excluded.add(Pair(firstRow, firstCol))

        if (settings.firstClickEmpty3x3 && (difficulty.totalCells - 9) >= totalMines) {
            excluded.addAll(getNeighbors(firstRow, firstCol, rows, cols))
        }

        val allCandidates = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val p = Pair(r, c)
                if (p !in excluded) {
                    allCandidates.add(p)
                }
            }
        }

        allCandidates.shuffle(Random)
        val minePositions = allCandidates.take(totalMines).toSet()

        // Create initial grid with mine positions
        val gridWithMines = Array(rows) { r ->
            Array(cols) { c ->
                val isMine = Pair(r, c) in minePositions
                Cell(row = r, col = c, isMine = isMine)
            }
        }

        // Calculate adjacent mines
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (!gridWithMines[r][c].isMine) {
                    var count = 0
                    for ((nr, nc) in getNeighbors(r, c, rows, cols)) {
                        if (gridWithMines[nr][nc].isMine) count++
                    }
                    gridWithMines[r][c] = gridWithMines[r][c].copy(adjacentMines = count)
                }
            }
        }

        return gridWithMines.map { it.toList() }.toList()
    }

    fun reveal(board: BoardState, row: Int, col: Int): BoardState {
        if (board.status == GameStatus.WON || board.status == GameStatus.LOST) return board

        var currentBoard = board
        if (currentBoard.status == GameStatus.IDLE) {
            val gridWithMines = if (settings.firstClickSafe) {
                placeMines(currentBoard.difficulty, row, col)
            } else {
                placeMines(currentBoard.difficulty, -1, -1)
            }
            currentBoard = currentBoard.copy(
                grid = gridWithMines,
                status = GameStatus.PLAYING
            )
        }

        val target = currentBoard[row, col] ?: return currentBoard
        if (target.visualState != CellVisualState.HIDDEN) {
            return currentBoard
        }

        val updatedGrid = currentBoard.grid.map { it.toMutableList() }.toMutableList()
        val newClicks = currentBoard.clicksCount + 1

        if (target.isMine) {
            // Hit a mine! Game lost.
            for (r in 0 until currentBoard.difficulty.rows) {
                for (c in 0 until currentBoard.difficulty.cols) {
                    val cell = updatedGrid[r][c]
                    if (r == row && c == col) {
                        updatedGrid[r][c] = cell.copy(
                            visualState = CellVisualState.REVEALED,
                            isTriggeredMine = true
                        )
                    } else if (cell.isMine && cell.visualState != CellVisualState.FLAGGED) {
                        updatedGrid[r][c] = cell.copy(visualState = CellVisualState.REVEALED)
                    } else if (!cell.isMine && cell.visualState == CellVisualState.FLAGGED) {
                        updatedGrid[r][c] = cell.copy(
                            visualState = CellVisualState.REVEALED,
                            isFalseFlag = true
                        )
                    } else if (cell.isMine && cell.visualState == CellVisualState.FLAGGED) {
                        updatedGrid[r][c] = cell.copy(
                            visualState = CellVisualState.REVEALED,
                            isCorrectFlag = true
                        )
                    }
                }
            }
            return currentBoard.copy(
                grid = updatedGrid.map { it.toList() },
                status = GameStatus.LOST,
                clicksCount = newClicks,
                triggeredCell = Pair(row, col)
            )
        }

        // Safe cell, BFS reveal
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(row, col))
        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(Pair(row, col))

        var newlyRevealed = 0

        while (queue.isNotEmpty()) {
            val (cr, cc) = queue.removeFirst()
            val cell = updatedGrid[cr][cc]

            if (cell.visualState == CellVisualState.HIDDEN) {
                updatedGrid[cr][cc] = cell.copy(visualState = CellVisualState.REVEALED)
                newlyRevealed++
            }

            if (cell.adjacentMines == 0 && !cell.isMine) {
                for ((nr, nc) in getNeighbors(cr, cc, currentBoard.difficulty.rows, currentBoard.difficulty.cols)) {
                    val neighbor = updatedGrid[nr][nc]
                    if (neighbor.visualState == CellVisualState.HIDDEN && !neighbor.isMine && Pair(nr, nc) !in visited) {
                        visited.add(Pair(nr, nc))
                        queue.add(Pair(nr, nc))
                    }
                }
            }
        }

        val totalRevealed = currentBoard.revealedCount + newlyRevealed
        val won = totalRevealed >= currentBoard.totalNonMines

        var finalGrid = updatedGrid.map { it.toList() }
        var finalFlagged = currentBoard.flaggedCount

        if (won) {
            // Auto flag remaining mines on win if enabled
            if (settings.autoFlagOnWin) {
                finalGrid = finalGrid.map { rowList ->
                    rowList.map { c ->
                        if (c.isMine && c.visualState != CellVisualState.FLAGGED) {
                            c.copy(visualState = CellVisualState.FLAGGED)
                        } else c
                    }
                }
                finalFlagged = currentBoard.difficulty.mines
            }
        }

        return currentBoard.copy(
            grid = finalGrid,
            status = if (won) GameStatus.WON else GameStatus.PLAYING,
            revealedCount = totalRevealed,
            flaggedCount = finalFlagged,
            clicksCount = newClicks
        )
    }

    fun toggleFlag(board: BoardState, row: Int, col: Int): BoardState {
        if (board.status == GameStatus.WON || board.status == GameStatus.LOST) return board

        val cell = board[row, col] ?: return board
        if (cell.isRevealed) return board

        var currentBoard = board
        if (currentBoard.status == GameStatus.IDLE) {
            // If flagging before first click, start game or place mines
            val gridWithMines = placeMines(currentBoard.difficulty, -1, -1)
            currentBoard = currentBoard.copy(
                grid = gridWithMines,
                status = GameStatus.PLAYING
            )
        }

        val target = currentBoard[row, col] ?: return currentBoard
        val nextState: CellVisualState
        var deltaFlag = 0

        when (target.visualState) {
            CellVisualState.HIDDEN -> {
                nextState = CellVisualState.FLAGGED
                deltaFlag = 1
            }
            CellVisualState.FLAGGED -> {
                if (settings.questionMarkEnabled) {
                    nextState = CellVisualState.QUESTIONED
                    deltaFlag = -1
                } else {
                    nextState = CellVisualState.HIDDEN
                    deltaFlag = -1
                }
            }
            CellVisualState.QUESTIONED -> {
                nextState = CellVisualState.HIDDEN
            }
            CellVisualState.REVEALED -> return currentBoard
        }

        val updatedGrid = currentBoard.grid.map { it.toMutableList() }.toMutableList()
        updatedGrid[row][col] = target.copy(visualState = nextState)

        return currentBoard.copy(
            grid = updatedGrid.map { it.toList() },
            flaggedCount = (currentBoard.flaggedCount + deltaFlag).coerceAtLeast(0),
            clicksCount = currentBoard.clicksCount + 1
        )
    }

    fun chord(board: BoardState, row: Int, col: Int): BoardState {
        if (!settings.chordingEnabled) return board
        if (board.status != GameStatus.PLAYING) return board

        val cell = board[row, col] ?: return board
        if (!cell.isRevealed || cell.adjacentMines == 0) return board

        val neighbors = getNeighbors(row, col, board.difficulty.rows, board.difficulty.cols)
        val flaggedNeighbors = neighbors.count { (nr, nc) ->
            board[nr, nc]?.visualState == CellVisualState.FLAGGED
        }

        if (flaggedNeighbors != cell.adjacentMines) {
            return board
        }

        // Reveal all non-flagged neighbors
        var workingBoard = board
        for ((nr, nc) in neighbors) {
            val nCell = workingBoard[nr, nc]
            if (nCell != null && nCell.visualState == CellVisualState.HIDDEN) {
                workingBoard = reveal(workingBoard, nr, nc)
                if (workingBoard.status == GameStatus.LOST) {
                    return workingBoard
                }
            }
        }

        return workingBoard
    }
}

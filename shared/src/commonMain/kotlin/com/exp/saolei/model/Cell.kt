package com.exp.saolei.model

enum class CellVisualState {
    HIDDEN,
    REVEALED,
    FLAGGED,
    QUESTIONED
}

data class Cell(
    val row: Int,
    val col: Int,
    val isMine: Boolean = false,
    val adjacentMines: Int = 0,
    val visualState: CellVisualState = CellVisualState.HIDDEN,
    val isTriggeredMine: Boolean = false,
    val isFalseFlag: Boolean = false,
    val isCorrectFlag: Boolean = false
) {
    val isHidden: Boolean get() = visualState == CellVisualState.HIDDEN
    val isRevealed: Boolean get() = visualState == CellVisualState.REVEALED
    val isFlagged: Boolean get() = visualState == CellVisualState.FLAGGED
    val isQuestioned: Boolean get() = visualState == CellVisualState.QUESTIONED
}

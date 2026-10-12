package com.exp.saolei.model

enum class DifficultyType {
    EASY,
    MEDIUM,
    HARD,
    CUSTOM
}

data class Difficulty(
    val type: DifficultyType,
    val name: String,
    val rows: Int,
    val cols: Int,
    val mines: Int
) {
    val totalCells: Int get() = rows * cols

    companion object {
        val EASY = Difficulty(
            type = DifficultyType.EASY,
            name = "初级",
            rows = 9,
            cols = 9,
            mines = 10
        )

        val MEDIUM = Difficulty(
            type = DifficultyType.MEDIUM,
            name = "中级",
            rows = 16,
            cols = 16,
            mines = 40
        )

        val HARD = Difficulty(
            type = DifficultyType.HARD,
            name = "高级",
            rows = 16,
            cols = 30,
            mines = 99
        )

        fun custom(rows: Int, cols: Int, mines: Int): Difficulty {
            val safeRows = rows.coerceIn(8, 50)
            val safeCols = cols.coerceIn(8, 30)
            val maxMines = (safeRows * safeCols - 9).coerceAtLeast(1)
            val safeMines = mines.coerceIn(1, maxMines)
            return Difficulty(
                type = DifficultyType.CUSTOM,
                name = "自定义 (${safeRows}×${safeCols})",
                rows = safeRows,
                cols = safeCols,
                mines = safeMines
            )
        }

        val allPredefined = listOf(EASY, MEDIUM, HARD)
    }
}

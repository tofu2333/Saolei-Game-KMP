package com.exp.saolei.model

data class GameRecord(
    val id: String,
    val difficultyType: DifficultyType,
    val difficultyName: String,
    val timeSeconds: Double,
    val timestamp: Long,
    val dateFormatted: String,
    val clicks: Int,
    val flagsPlaced: Int
) {
    val formattedTime: String
        get() {
            val totalSec = timeSeconds.toInt()
            val minutes = totalSec / 60
            val seconds = totalSec % 60
            val millisTenth = ((timeSeconds - totalSec) * 10).toInt().coerceIn(0, 9)
            return if (minutes > 0) {
                "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}.${millisTenth}"
            } else {
                "${seconds}.${millisTenth}s"
            }
        }
}

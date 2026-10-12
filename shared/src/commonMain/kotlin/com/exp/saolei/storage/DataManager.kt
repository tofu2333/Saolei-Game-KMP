package com.exp.saolei.storage

import com.exp.saolei.model.DifficultyType
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.model.GameRecord
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.ThemeMode
import kotlin.random.Random

object DataManager {
    private const val KEY_SETTINGS = "saolei_settings_v1"
    private const val KEY_LEADERBOARD_PREFIX = "saolei_lb_"

    fun loadSettings(): GameSettings {
        val raw = PlatformStorage.getString(KEY_SETTINGS, "")
        if (raw.isBlank()) return GameSettings()
        return try {
            val parts = raw.split(";")
            val map = parts.associate {
                val kv = it.split("=")
                if (kv.size == 2) kv[0] to kv[1] else "" to ""
            }
            GameSettings(
                firstClickSafe = map["firstClickSafe"]?.toBooleanStrictOrNull() ?: true,
                firstClickEmpty3x3 = map["firstClickEmpty3x3"]?.toBooleanStrictOrNull() ?: true,
                chordingEnabled = map["chordingEnabled"]?.toBooleanStrictOrNull() ?: true,
                questionMarkEnabled = map["questionMarkEnabled"]?.toBooleanStrictOrNull() ?: true,
                vibrationEnabled = map["vibrationEnabled"]?.toBooleanStrictOrNull() ?: true,
                sfxEnabled = map["sfxEnabled"]?.toBooleanStrictOrNull() ?: true,
                sfxVolume = map["sfxVolume"]?.toFloatOrNull() ?: 0.8f,
                bgmEnabled = map["bgmEnabled"]?.toBooleanStrictOrNull() ?: true,
                bgmVolume = map["bgmVolume"]?.toFloatOrNull() ?: 0.4f,
                themeMode = map["themeMode"]?.let {
                    try { ThemeMode.valueOf(it) } catch (e: Exception) { ThemeMode.SYSTEM }
                } ?: ThemeMode.SYSTEM,
                defaultInteractionMode = map["defaultInteractionMode"]?.let {
                    try { GameInteractionMode.valueOf(it) } catch (e: Exception) { GameInteractionMode.DIG }
                } ?: GameInteractionMode.DIG,
                autoFlagOnWin = map["autoFlagOnWin"]?.toBooleanStrictOrNull() ?: true,
                cellSize = map["cellSize"]?.toIntOrNull() ?: 40
            )
        } catch (e: Exception) {
            GameSettings()
        }
    }

    fun saveSettings(settings: GameSettings) {
        val raw = buildString {
            append("firstClickSafe=${settings.firstClickSafe};")
            append("firstClickEmpty3x3=${settings.firstClickEmpty3x3};")
            append("chordingEnabled=${settings.chordingEnabled};")
            append("questionMarkEnabled=${settings.questionMarkEnabled};")
            append("vibrationEnabled=${settings.vibrationEnabled};")
            append("sfxEnabled=${settings.sfxEnabled};")
            append("sfxVolume=${settings.sfxVolume};")
            append("bgmEnabled=${settings.bgmEnabled};")
            append("bgmVolume=${settings.bgmVolume};")
            append("themeMode=${settings.themeMode.name};")
            append("defaultInteractionMode=${settings.defaultInteractionMode.name};")
            append("autoFlagOnWin=${settings.autoFlagOnWin};")
            append("cellSize=${settings.cellSize}")
        }
        PlatformStorage.putString(KEY_SETTINGS, raw)
    }

    fun getRecords(difficultyType: DifficultyType): List<GameRecord> {
        val raw = PlatformStorage.getString(KEY_LEADERBOARD_PREFIX + difficultyType.name, "")
        if (raw.isBlank()) return emptyList()
        return try {
            raw.split("\n")
                .filter { it.isNotBlank() }
                .mapNotNull { line ->
                    val tokens = line.split("|")
                    if (tokens.size >= 8) {
                        GameRecord(
                            id = tokens[0],
                            difficultyType = DifficultyType.valueOf(tokens[1]),
                            difficultyName = tokens[2],
                            timeSeconds = tokens[3].toDoubleOrNull() ?: 0.0,
                            timestamp = tokens[4].toLongOrNull() ?: 0L,
                            dateFormatted = tokens[5],
                            clicks = tokens[6].toIntOrNull() ?: 0,
                            flagsPlaced = tokens[7].toIntOrNull() ?: 0
                        )
                    } else null
                }
                .sortedBy { it.timeSeconds }
                .take(20)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Records a win and keeps only the top 20 shortest time records.
     * Returns: (isNewPersonalBest, rankInTop20 (1-based, or null if not in top 20))
     */
    fun recordWin(
        difficultyType: DifficultyType,
        difficultyName: String,
        timeSeconds: Double,
        clicks: Int,
        flagsPlaced: Int,
        dateFormatted: String
    ): Pair<Boolean, Int?> {
        val currentRecords = getRecords(difficultyType).toMutableList()
        val isFirstRecord = currentRecords.isEmpty()
        val bestTime = currentRecords.firstOrNull()?.timeSeconds
        val isNewPersonalBest = isFirstRecord || (bestTime != null && timeSeconds < bestTime)

        val newRecord = GameRecord(
            id = "rec_${Random.nextLong()}",
            difficultyType = difficultyType,
            difficultyName = difficultyName,
            timeSeconds = timeSeconds,
            timestamp = 0L,
            dateFormatted = dateFormatted,
            clicks = clicks,
            flagsPlaced = flagsPlaced
        )

        currentRecords.add(newRecord)
        currentRecords.sortBy { it.timeSeconds }

        val top20 = currentRecords.take(20)
        val rankIndex = top20.indexOfFirst { it.id == newRecord.id }
        val rank = if (rankIndex >= 0) rankIndex + 1 else null

        // Save back
        val raw = top20.joinToString("\n") { r ->
            "${r.id}|${r.difficultyType.name}|${r.difficultyName}|${r.timeSeconds}|${r.timestamp}|${r.dateFormatted}|${r.clicks}|${r.flagsPlaced}"
        }
        PlatformStorage.putString(KEY_LEADERBOARD_PREFIX + difficultyType.name, raw)

        return Pair(isNewPersonalBest, rank)
    }

    fun clearRecords(difficultyType: DifficultyType) {
        PlatformStorage.putString(KEY_LEADERBOARD_PREFIX + difficultyType.name, "")
    }

    fun getBestTime(difficultyType: DifficultyType): Double? {
        return getRecords(difficultyType).firstOrNull()?.timeSeconds
    }
}

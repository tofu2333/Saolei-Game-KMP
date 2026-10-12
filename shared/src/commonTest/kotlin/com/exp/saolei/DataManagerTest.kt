package com.exp.saolei

import com.exp.saolei.model.DifficultyType
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.ThemeMode
import com.exp.saolei.storage.DataManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DataManagerTest {

    @Test
    fun testSettingsSaveAndLoad() {
        val original = GameSettings(
            firstClickSafe = true,
            firstClickEmpty3x3 = false,
            sfxVolume = 0.65f,
            bgmVolume = 0.35f,
            themeMode = ThemeMode.DARK,
            cellSize = 48
        )
        DataManager.saveSettings(original)
        val loaded = DataManager.loadSettings()

        assertEquals(original.firstClickSafe, loaded.firstClickSafe)
        assertEquals(original.firstClickEmpty3x3, loaded.firstClickEmpty3x3)
        assertEquals(0.65f, loaded.sfxVolume)
        assertEquals(0.35f, loaded.bgmVolume)
        assertEquals(ThemeMode.DARK, loaded.themeMode)
        assertEquals(48, loaded.cellSize)
    }

    @Test
    fun testTop20LeaderboardRecordsLimitAndSort() {
        DataManager.clearRecords(DifficultyType.EASY)

        // Add 25 records with various times from 100s down to 10s
        for (i in 1..25) {
            val time = (100 - i * 3).toDouble()
            DataManager.recordWin(
                difficultyType = DifficultyType.EASY,
                difficultyName = "初级",
                timeSeconds = time,
                clicks = 10,
                flagsPlaced = 10,
                dateFormatted = "2026-10-12"
            )
        }

        val records = DataManager.getRecords(DifficultyType.EASY)
        // Must contain at most 20 records
        assertEquals(20, records.size)

        // Must be sorted strictly ascending by timeSeconds
        for (i in 0 until records.size - 1) {
            assertTrue(records[i].timeSeconds <= records[i + 1].timeSeconds)
        }

        // The best record should be the lowest time
        val best = DataManager.getBestTime(DifficultyType.EASY)
        assertEquals(records.first().timeSeconds, best)

        // Clean up
        DataManager.clearRecords(DifficultyType.EASY)
        assertTrue(DataManager.getRecords(DifficultyType.EASY).isEmpty())
    }
}

package com.exp.saolei.model

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

data class GameSettings(
    val firstClickSafe: Boolean = true,
    val firstClickEmpty3x3: Boolean = true,
    val chordingEnabled: Boolean = true,
    val questionMarkEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val sfxEnabled: Boolean = true,
    val sfxVolume: Float = 0.8f,
    val bgmEnabled: Boolean = true,
    val bgmVolume: Float = 0.4f,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val defaultInteractionMode: GameInteractionMode = GameInteractionMode.DIG,
    val autoFlagOnWin: Boolean = true,
    val cellSize: Int = 40
)

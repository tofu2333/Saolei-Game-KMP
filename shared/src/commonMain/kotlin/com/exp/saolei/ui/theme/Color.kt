package com.exp.saolei.ui.theme

import androidx.compose.ui.graphics.Color

// Brand Accents
val MinePrimary = Color(0xFF3B82F6)
val MinePrimaryDark = Color(0xFF1D4ED8)
val MineAccent = Color(0xFF10B981)
val MineDanger = Color(0xFFEF4444)
val MineWarning = Color(0xFFF59E0B)

// Light Theme Colors
val LightBackground = Color(0xFFF1F5F9)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFE2E8F0)
val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF64748B)
val LightTileHidden = Color(0xFFE2E8F0)
val LightTileRevealed = Color(0xFFF8FAFC)
val LightTileBorder = Color(0xFFCBD5E1)

// Dark Theme Colors
val DarkPrimary = Color(0xFF60A5FA) // Pastel/Sky blue (Tone 80) for high dark-mode contrast
val DarkBackground = Color(0xFF0B1120)
val DarkSurface = Color(0xFF151F32)
val DarkSurfaceVariant = Color(0xFF1E293B)
val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFFCBD5E1) // High-contrast slate 300 for clear readability
val DarkTileHidden = Color(0xFF243046)
val DarkTileRevealed = Color(0xFF111827)
val DarkTileBorder = Color(0xFF334155)

// Number Colors (1 - 8)
val Number1Light = Color(0xFF1D4ED8) // Deep Blue
val Number1Dark = Color(0xFF60A5FA)
val Number2Light = Color(0xFF047857) // Deep Emerald
val Number2Dark = Color(0xFF34D399)
val Number3Light = Color(0xFFDC2626) // Red
val Number3Dark = Color(0xFFF87171)
val Number4Light = Color(0xFF6D28D9) // Deep Purple
val Number4Dark = Color(0xFFA78BFA)
val Number5Light = Color(0xFFB45309) // Deep Amber
val Number5Dark = Color(0xFFFBBF24)
val Number6Light = Color(0xFF0F766E) // Deep Teal
val Number6Dark = Color(0xFF2DD4BF)
val Number7Light = Color(0xFF1F2937) // Charcoal
val Number7Dark = Color(0xFFD1D5DB)
val Number8Light = Color(0xFF4B5563) // Slate
val Number8Dark = Color(0xFF94A3B8)

fun getNumberColor(number: Int, isDark: Boolean): Color {
    return when (number) {
        1 -> if (isDark) Number1Dark else Number1Light
        2 -> if (isDark) Number2Dark else Number2Light
        3 -> if (isDark) Number3Dark else Number3Light
        4 -> if (isDark) Number4Dark else Number4Light
        5 -> if (isDark) Number5Dark else Number5Light
        6 -> if (isDark) Number6Dark else Number6Light
        7 -> if (isDark) Number7Dark else Number7Light
        8 -> if (isDark) Number8Dark else Number8Light
        else -> if (isDark) DarkTextPrimary else LightTextPrimary
    }
}

package com.exp.saolei

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.exp.saolei.audio.AudioPlayer
import com.exp.saolei.model.GameSettings
import com.exp.saolei.presentation.GameIntent
import com.exp.saolei.presentation.GameViewModel
import com.exp.saolei.storage.DataManager
import com.exp.saolei.ui.screens.GameScreen
import com.exp.saolei.ui.screens.LeaderboardScreen
import com.exp.saolei.ui.screens.RulesScreen
import com.exp.saolei.ui.screens.SettingsScreen
import com.exp.saolei.ui.theme.SaoleiTheme

enum class Screen {
    GAME,
    LEADERBOARD,
    RULES,
    SETTINGS
}

@Composable
fun App() {
    var settings by remember { mutableStateOf(DataManager.loadSettings()) }
    var currentScreen by remember { mutableStateOf(Screen.GAME) }

    val audioPlayer = remember { AudioPlayer() }
    val gameViewModel = remember { GameViewModel(settings) }

    // Synchronize BGM playback with settings
    LaunchedEffect(settings.bgmEnabled, settings.bgmVolume) {
        if (settings.bgmEnabled) {
            audioPlayer.startBgm(settings.bgmVolume)
        } else {
            audioPlayer.stopBgm()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }

    SaoleiTheme(themeMode = settings.themeMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.GAME -> GameScreen(
                    viewModel = gameViewModel,
                    audioPlayer = audioPlayer,
                    onOpenLeaderboard = { currentScreen = Screen.LEADERBOARD },
                    onOpenRules = { currentScreen = Screen.RULES },
                    onOpenSettings = { currentScreen = Screen.SETTINGS }
                )

                Screen.LEADERBOARD -> LeaderboardScreen(
                    onBack = { currentScreen = Screen.GAME }
                )

                Screen.RULES -> RulesScreen(
                    onBack = { currentScreen = Screen.GAME }
                )

                Screen.SETTINGS -> SettingsScreen(
                    settings = settings,
                    onSettingsChanged = { newSettings ->
                        settings = newSettings
                        gameViewModel.processIntent(GameIntent.UpdateSettings(newSettings))
                    },
                    onBack = { currentScreen = Screen.GAME }
                )
            }
        }
    }
}
package com.exp.saolei.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.exp.saolei.audio.AudioPlayer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.exp.saolei.presentation.GameEffect
import com.exp.saolei.presentation.GameIntent
import com.exp.saolei.presentation.GameViewModel
import com.exp.saolei.ui.components.ControlBar
import com.exp.saolei.ui.components.CustomGameDialog
import com.exp.saolei.ui.components.DefeatDialog
import com.exp.saolei.ui.components.GameHeader
import com.exp.saolei.ui.components.MinefieldView
import com.exp.saolei.ui.components.VictoryDialog
import kotlinx.coroutines.flow.collectLatest

import com.exp.saolei.ui.theme.LocalIsDark

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    audioPlayer: AudioPlayer,
    onOpenLeaderboard: () -> Unit,
    onOpenRules: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = LocalIsDark.current
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current

    // Listen to one-off MVI UI Effects
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is GameEffect.PlaySound -> {
                    if (uiState.settings.sfxEnabled) {
                        audioPlayer.playSound(effect.sound, uiState.settings.sfxVolume)
                    }
                }
                is GameEffect.TriggerHaptic -> {
                    if (uiState.settings.vibrationEnabled) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header (MD3 Card & Actions) with status bar insets
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                GameHeader(
                    difficulty = uiState.difficulty,
                    remainingMines = uiState.boardState.remainingMines,
                    elapsedSeconds = uiState.elapsedSeconds,
                    gameStatus = uiState.boardState.status,
                    onRestart = { viewModel.processIntent(GameIntent.RestartGame()) },
                    onSelectDifficulty = { diff -> viewModel.processIntent(GameIntent.ChangeDifficulty(diff)) },
                    onOpenCustomDialog = { viewModel.processIntent(GameIntent.SetCustomDialogVisible(true)) },
                    onOpenLeaderboard = onOpenLeaderboard,
                    onOpenRules = onOpenRules,
                    onOpenSettings = onOpenSettings,
                    isDark = isDark,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Center Zoomable & Pannable Minefield (strictly clipped to viewport)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                MinefieldView(
                    board = uiState.boardState,
                    interactionMode = uiState.interactionMode,
                    cellSize = uiState.settings.cellSize,
                    scale = uiState.scale,
                    offset = uiState.offset,
                    onTransform = { zoom, pan ->
                        viewModel.processIntent(GameIntent.UpdateZoomPan(zoom, pan))
                    },
                    onCellClick = { r, c ->
                        viewModel.processIntent(GameIntent.CellClick(r, c))
                    },
                    onCellLongClick = { r, c ->
                        viewModel.processIntent(GameIntent.CellLongClick(r, c))
                    },
                    onChord = { r, c ->
                        viewModel.processIntent(GameIntent.ChordClick(r, c))
                    },
                    onFitScaleCalculated = { fitScale ->
                        viewModel.processIntent(GameIntent.SetFitScale(fitScale))
                    },
                    isDark = isDark
                )
            }

            // Bottom Ergonomic Control Bar with navigation bar insets
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                ControlBar(
                    interactionMode = uiState.interactionMode,
                    onToggleMode = { mode ->
                        viewModel.processIntent(GameIntent.ToggleInteractionMode(mode))
                    },
                    zoomScale = uiState.scale,
                    onZoomIn = { viewModel.processIntent(GameIntent.ZoomIn) },
                    onZoomOut = { viewModel.processIntent(GameIntent.ZoomOut) },
                    onResetView = { viewModel.processIntent(GameIntent.ResetView) },
                    isDark = isDark,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Custom Difficulty Dialog
        if (uiState.showCustomDialog) {
            CustomGameDialog(
                onConfirm = { customDiff ->
                    viewModel.processIntent(GameIntent.SetCustomDialogVisible(false))
                    viewModel.processIntent(GameIntent.ChangeDifficulty(customDiff))
                },
                onDismiss = {
                    viewModel.processIntent(GameIntent.SetCustomDialogVisible(false))
                },
                isDark = isDark
            )
        }

        // Victory Dialog (10 Distinct Styles)
        uiState.activeVictoryStyle?.let { style ->
            VictoryDialog(
                style = style,
                timeSeconds = uiState.exactElapsedSeconds,
                difficulty = uiState.difficulty,
                clicks = uiState.boardState.clicksCount,
                flags = uiState.boardState.flaggedCount,
                isNewRecord = uiState.isNewRecord,
                rank = uiState.userRank,
                onPlayAgain = {
                    viewModel.processIntent(GameIntent.DismissVictoryDialog)
                    viewModel.processIntent(GameIntent.RestartGame())
                },
                onReviewBoard = {
                    viewModel.processIntent(GameIntent.DismissVictoryDialog)
                },
                onViewLeaderboard = {
                    viewModel.processIntent(GameIntent.DismissVictoryDialog)
                    onOpenLeaderboard()
                }
            )
        }

        // Defeat Dialog
        if (uiState.showDefeatDialog) {
            DefeatDialog(
                timeSeconds = uiState.exactElapsedSeconds,
                difficulty = uiState.difficulty,
                revealedCount = uiState.boardState.revealedCount,
                totalNonMines = uiState.boardState.totalNonMines,
                onTryAgain = {
                    viewModel.processIntent(GameIntent.DismissDefeatDialog)
                    viewModel.processIntent(GameIntent.RestartGame())
                },
                onInspectBoard = {
                    viewModel.processIntent(GameIntent.DismissDefeatDialog)
                }
            )
        }
    }
}

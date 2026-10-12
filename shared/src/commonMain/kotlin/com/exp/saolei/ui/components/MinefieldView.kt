package com.exp.saolei.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.exp.saolei.engine.BoardState
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.ui.theme.DarkSurface
import com.exp.saolei.ui.theme.LightSurface
import com.exp.saolei.ui.theme.LocalIsDark

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.isTertiaryPressed

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MinefieldView(
    board: BoardState,
    interactionMode: GameInteractionMode,
    onCellClick: (row: Int, col: Int) -> Unit,
    onCellLongClick: (row: Int, col: Int) -> Unit,
    onChord: (row: Int, col: Int) -> Unit,
    scale: Float,
    offset: Offset,
    onTransform: (zoomChange: Float, panChange: Offset) -> Unit,
    modifier: Modifier = Modifier,
    cellSize: Int = 40,
    isDark: Boolean = LocalIsDark.current,
    onFitScaleCalculated: ((Float) -> Unit)? = null,
    onCellRightClick: (row: Int, col: Int) -> Unit = onCellLongClick
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize().clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        val rows = board.difficulty.rows
        val cols = board.difficulty.cols

        val baseCellSize: Dp = cellSize.coerceIn(24, 80).dp
        val cellSpacing = 2.dp
        val boardPadding = 6.dp

        val totalBoardWidth = (baseCellSize + cellSpacing) * cols - cellSpacing + boardPadding * 2
        val totalBoardHeight = (baseCellSize + cellSpacing) * rows - cellSpacing + boardPadding * 2

        val availableWidth = (maxWidth - 24.dp).coerceAtLeast(60.dp)
        val availableHeight = (maxHeight - 24.dp).coerceAtLeast(60.dp)

        val fitScaleX = availableWidth / totalBoardWidth
        val fitScaleY = availableHeight / totalBoardHeight
        val fitScale = minOf(fitScaleX, fitScaleY, 1.0f).coerceIn(0.12f, 1.0f)

        LaunchedEffect(fitScale) {
            onFitScaleCalculated?.invoke(fitScale)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        onTransform(zoom, pan)
                    }
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val delta = event.changes.firstOrNull()?.scrollDelta?.y ?: 0f
                            if (delta != 0f) {
                                val zoomFactor = if (delta < 0) 1.15f else 0.87f
                                onTransform(zoomFactor, Offset.Zero)
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center, unbounded = true)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    },
                shape = RoundedCornerShape(16.dp),
                color = if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0),
                border = BorderStroke(1.5.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)),
                shadowElevation = 4.dp
            ) {
                Box(modifier = Modifier.padding(boardPadding)) {
                    Column {
                        for (r in 0 until rows) {
                            Row {
                                for (c in 0 until cols) {
                                    val cell = board[r, c]
                                    if (cell != null) {
                                        Box(
                                            modifier = Modifier
                                                .requiredSize(baseCellSize + cellSpacing)
                                                .padding(cellSpacing / 2)
                                                .pointerInput(cell, r, c) {
                                                    detectSecondaryGestures(
                                                        onRightClick = { onCellRightClick(r, c) },
                                                        onMiddleClick = {
                                                            if (cell.isRevealed && cell.adjacentMines > 0) {
                                                                onChord(r, c)
                                                            }
                                                        }
                                                    )
                                                }
                                                .pointerInput(cell, r, c) {
                                                    detectTapGestures(
                                                        onTap = {
                                                            if (cell.isRevealed) {
                                                                if (cell.adjacentMines > 0) {
                                                                    onChord(r, c)
                                                                }
                                                            } else {
                                                                onCellClick(r, c)
                                                            }
                                                        },
                                                        onLongPress = {
                                                            if (!cell.isRevealed) {
                                                                onCellLongClick(r, c)
                                                            }
                                                        }
                                                    )
                                                }
                                        ) {
                                            CellView(
                                                cell = cell,
                                                size = baseCellSize,
                                                isDark = isDark
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun PointerInputScope.detectSecondaryGestures(
    onRightClick: () -> Unit,
    onMiddleClick: (() -> Unit)? = null
) {
    awaitEachGesture {
        while (true) {
            val event = awaitPointerEvent(PointerEventPass.Initial)
            val isRightClick = event.buttons.isSecondaryPressed ||
                (event.keyboardModifiers.isCtrlPressed && event.buttons.isPrimaryPressed)
            val isMiddleClick = event.buttons.isTertiaryPressed

            if ((isRightClick || isMiddleClick) && event.changes.any { it.changedToDown() }) {
                val down = event.changes.first { it.changedToDown() }
                down.consume()
                if (isMiddleClick) {
                    onMiddleClick?.invoke()
                } else {
                    onRightClick()
                }
                waitForUpOrCancellation(PointerEventPass.Initial)?.consume()
                break
            }
        }
    }
}

package com.exp.saolei.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.model.Cell
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.ui.theme.DarkTileBorder
import com.exp.saolei.ui.theme.DarkTileHidden
import com.exp.saolei.ui.theme.DarkTileHiddenBottom
import com.exp.saolei.ui.theme.DarkTileHiddenTop
import com.exp.saolei.ui.theme.DarkTileRevealed
import com.exp.saolei.ui.theme.LightTileBorder
import com.exp.saolei.ui.theme.LightTileHidden
import com.exp.saolei.ui.theme.LightTileHiddenBottom
import com.exp.saolei.ui.theme.LightTileHiddenTop
import com.exp.saolei.ui.theme.LightTileRevealed
import com.exp.saolei.ui.theme.LocalIsDark
import com.exp.saolei.ui.theme.MineDanger
import com.exp.saolei.ui.theme.MineWarning
import com.exp.saolei.ui.theme.getNumberColor

@Composable
fun CellView(
    cell: Cell,
    size: Dp,
    modifier: Modifier = Modifier,
    isDark: Boolean = LocalIsDark.current
) {
    val cornerRadius = (size * 0.14f).coerceIn(2.dp, 6.dp)
    val shape = RoundedCornerShape(cornerRadius)

    val bgColor by animateColorAsState(
        targetValue = when {
            cell.isTriggeredMine -> MineDanger
            cell.isCorrectFlag -> if (isDark) Color(0xFF143026) else Color(0xFFDCFCE7)
            cell.isRevealed -> if (isDark) DarkTileRevealed else LightTileRevealed
            else -> if (isDark) DarkTileHidden else LightTileHidden
        },
        animationSpec = tween(durationMillis = 150)
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(bgColor)
            .then(
                if (cell.isHidden || (cell.isFlagged && !cell.isCorrectFlag && !cell.isFalseFlag) || cell.isQuestioned) {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = if (isDark) DarkTileBorder else LightTileBorder,
                            shape = shape
                        )
                        .background(
                            Brush.verticalGradient(
                                colors = if (isDark) {
                                    listOf(DarkTileHiddenTop, DarkTileHiddenBottom)
                                } else {
                                    listOf(LightTileHiddenTop, LightTileHiddenBottom)
                                }
                            )
                        )
                } else {
                    Modifier.border(
                        width = if (cell.isCorrectFlag) 1.dp else 0.5.dp,
                        color = if (cell.isCorrectFlag) {
                            if (isDark) Color(0xFF10B981) else Color(0xFF34D399)
                        } else {
                            if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                        },
                        shape = shape
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when (cell.visualState) {
            CellVisualState.HIDDEN -> {
                // Bevel highlight for tactile feel
                BevelHighlight(size = size, isDark = isDark)
            }

            CellVisualState.FLAGGED -> {
                if (cell.isCorrectFlag) {
                    CorrectFlaggedMineIcon(size = size, isDark = isDark)
                } else if (cell.isFalseFlag) {
                    FalseFlagIcon(size = size)
                } else {
                    BevelHighlight(size = size, isDark = isDark)
                    FlagIcon(size = size)
                }
            }

            CellVisualState.QUESTIONED -> {
                BevelHighlight(size = size, isDark = isDark)
                Text(
                    text = "?",
                    color = MineWarning,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.55f).sp
                )
            }

            CellVisualState.REVEALED -> {
                when {
                    cell.isTriggeredMine -> {
                        MineIcon(size = size, isDetonated = true, isDark = isDark)
                    }

                    cell.isCorrectFlag -> {
                        CorrectFlaggedMineIcon(size = size, isDark = isDark)
                    }

                    cell.isFalseFlag -> {
                        // Flag crossed out with red X
                        FalseFlagIcon(size = size)
                    }

                    cell.isMine -> {
                        MineIcon(size = size, isDetonated = false, isDark = isDark)
                    }

                    cell.adjacentMines > 0 -> {
                        Text(
                            text = cell.adjacentMines.toString(),
                            color = getNumberColor(cell.adjacentMines, isDark),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = (size.value * 0.58f).sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BevelHighlight(size: Dp, isDark: Boolean) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeW = 1.5.dp.toPx()
        val highlightColor = if (isDark) Color(0x33FFFFFF) else Color(0x66FFFFFF)
        val shadowColor = if (isDark) Color(0x44000000) else Color(0x22000000)

        // Top and Left edge highlight
        drawLine(
            color = highlightColor,
            start = Offset(strokeW, strokeW),
            end = Offset(this.size.width - strokeW, strokeW),
            strokeWidth = strokeW
        )
        drawLine(
            color = highlightColor,
            start = Offset(strokeW, strokeW),
            end = Offset(strokeW, this.size.height - strokeW),
            strokeWidth = strokeW
        )

        // Bottom and Right edge shadow
        drawLine(
            color = shadowColor,
            start = Offset(this.size.width - strokeW, strokeW),
            end = Offset(this.size.width - strokeW, this.size.height - strokeW),
            strokeWidth = strokeW
        )
        drawLine(
            color = shadowColor,
            start = Offset(strokeW, this.size.height - strokeW),
            end = Offset(this.size.width - strokeW, this.size.height - strokeW),
            strokeWidth = strokeW
        )
    }
}

@Composable
private fun FlagIcon(size: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height

        val poleX = w * 0.36f
        val poleTop = h * 0.20f
        val poleBottom = h * 0.82f
        val strokeW = (w * 0.08f).coerceAtLeast(2f)

        // Golden pole
        drawLine(
            color = Color(0xFFD97706),
            start = Offset(poleX, poleTop),
            end = Offset(poleX, poleBottom),
            strokeWidth = strokeW,
            cap = StrokeCap.Round
        )

        // Base stand
        val baseLeft = w * 0.24f
        val baseRight = w * 0.58f
        drawLine(
            color = Color(0xFF92400E),
            start = Offset(baseLeft, poleBottom),
            end = Offset(baseRight, poleBottom),
            strokeWidth = strokeW * 1.2f,
            cap = StrokeCap.Round
        )

        // Red triangular flag
        val flagPath = Path().apply {
            moveTo(poleX, poleTop)
            lineTo(w * 0.82f, poleTop + h * 0.22f)
            lineTo(poleX, poleTop + h * 0.44f)
            close()
        }
        drawPath(
            path = flagPath,
            color = Color(0xFFEF4444)
        )
    }
}

@Composable
private fun MineIcon(size: Dp, isDetonated: Boolean, isDark: Boolean = LocalIsDark.current) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)
        val radius = w * 0.26f

        val bodyColor = if (isDetonated) Color.White else if (isDark) Color(0xFFE2E8F0) else Color(0xFF1E293B)
        val spikeColor = if (isDetonated) Color.White else if (isDark) Color(0xFFCBD5E1) else Color(0xFF0F172A)
        val spikeLength = w * 0.16f
        val strokeW = (w * 0.09f).coerceAtLeast(2f)

        // Spikes in 8 directions
        val angles = listOf(0.0, 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0)
        for (deg in angles) {
            val rad = deg * (kotlin.math.PI / 180.0)
            val start = Offset(
                center.x + (radius * kotlin.math.cos(rad)).toFloat(),
                center.y + (radius * kotlin.math.sin(rad)).toFloat()
            )
            val end = Offset(
                center.x + ((radius + spikeLength) * kotlin.math.cos(rad)).toFloat(),
                center.y + ((radius + spikeLength) * kotlin.math.sin(rad)).toFloat()
            )
            drawLine(
                color = spikeColor,
                start = start,
                end = end,
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
        }

        // Mine main circle
        drawCircle(
            color = bodyColor,
            radius = radius,
            center = center
        )

        // Light reflection dot
        drawCircle(
            color = if (isDetonated) Color(0xFFFFCCCC) else Color(0x88FFFFFF),
            radius = radius * 0.32f,
            center = Offset(center.x - radius * 0.35f, center.y - radius * 0.35f)
        )
    }
}

@Composable
private fun FalseFlagIcon(size: Dp) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        FlagIcon(size = size)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW = (this.size.width * 0.10f).coerceAtLeast(2.5f)
            val pad = this.size.width * 0.22f
            drawLine(
                color = MineDanger,
                start = Offset(pad, pad),
                end = Offset(this.size.width - pad, this.size.height - pad),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
            drawLine(
                color = MineDanger,
                start = Offset(this.size.width - pad, pad),
                end = Offset(pad, this.size.height - pad),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun CorrectFlaggedMineIcon(size: Dp, isDark: Boolean) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Layer 1: Mine at base
        MineIcon(size = size, isDetonated = false, isDark = isDark)

        // Layer 2: Flag layered on top with crisp outline for high contrast
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height

            val poleX = w * 0.36f
            val poleTop = h * 0.18f
            val poleBottom = h * 0.80f
            val strokeW = (w * 0.08f).coerceAtLeast(2f)

            // Outline for pole to separate from mine body
            val outlineColor = if (isDark) Color(0xFF0F172A) else Color.White
            drawLine(
                color = outlineColor,
                start = Offset(poleX, poleTop - 1f),
                end = Offset(poleX, poleBottom + 1f),
                strokeWidth = strokeW + 2.5f,
                cap = StrokeCap.Round
            )

            // Golden pole
            drawLine(
                color = Color(0xFFF59E0B),
                start = Offset(poleX, poleTop),
                end = Offset(poleX, poleBottom),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )

            // Flag outline
            val flagPath = Path().apply {
                moveTo(poleX, poleTop)
                lineTo(w * 0.82f, poleTop + h * 0.20f)
                lineTo(poleX, poleTop + h * 0.40f)
                close()
            }
            drawPath(
                path = flagPath,
                color = outlineColor,
                style = Stroke(width = 2.5f)
            )
            // Red flag fill
            drawPath(
                path = flagPath,
                color = Color(0xFFEF4444)
            )

            // Bottom-right success badge (green circle with white checkmark)
            val badgeRadius = w * 0.17f
            val badgeCenter = Offset(w - badgeRadius - 2f, h - badgeRadius - 2f)

            // Badge shadow / outline
            drawCircle(
                color = outlineColor,
                radius = badgeRadius + 1.5f,
                center = badgeCenter
            )
            // Green badge
            drawCircle(
                color = Color(0xFF10B981),
                radius = badgeRadius,
                center = badgeCenter
            )
            // White checkmark
            val checkPath = Path().apply {
                moveTo(badgeCenter.x - badgeRadius * 0.48f, badgeCenter.y - badgeRadius * 0.05f)
                lineTo(badgeCenter.x - badgeRadius * 0.10f, badgeCenter.y + badgeRadius * 0.38f)
                lineTo(badgeCenter.x + badgeRadius * 0.50f, badgeCenter.y - badgeRadius * 0.38f)
            }
            drawPath(
                path = checkPath,
                color = Color.White,
                style = Stroke(
                    width = (w * 0.055f).coerceAtLeast(1.5f),
                    cap = StrokeCap.Round
                )
            )
        }
    }
}


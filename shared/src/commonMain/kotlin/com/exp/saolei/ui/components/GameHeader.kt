package com.exp.saolei.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameStatus
import com.exp.saolei.ui.theme.LocalIsDark
import com.exp.saolei.ui.theme.MineDanger
import com.exp.saolei.ui.theme.MinePrimary

@Composable
fun GameHeader(
    difficulty: Difficulty,
    remainingMines: Int,
    elapsedSeconds: Int,
    gameStatus: GameStatus,
    onRestart: () -> Unit,
    onSelectDifficulty: (Difficulty) -> Unit,
    onOpenCustomDialog: () -> Unit,
    onOpenLeaderboard: () -> Unit,
    onOpenRules: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = LocalIsDark.current
) {
    var difficultyMenuExpanded by remember { mutableStateOf(false) }

    val cardShape = RoundedCornerShape(20.dp)
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape)
            .animateContentSize(),
        shape = cardShape,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Top Row: Difficulty chip and navigation actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    AssistChip(
                        onClick = { difficultyMenuExpanded = true },
                        label = {
                            Text(
                                text = "${difficulty.name} (${difficulty.rows}×${difficulty.cols})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                        },
                        trailingIcon = {
                            Text("▼", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp)
                    )

                    DropdownMenu(
                        expanded = difficultyMenuExpanded,
                        onDismissRequest = { difficultyMenuExpanded = false }
                    ) {
                        Difficulty.allPredefined.forEach { diff ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${diff.name} (${diff.rows}×${diff.cols}, ${diff.mines} 雷)",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                onClick = {
                                    difficultyMenuExpanded = false
                                    onSelectDifficulty(diff)
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "自定义难度...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                difficultyMenuExpanded = false
                                onOpenCustomDialog()
                            }
                        )
                    }
                }

                // Header Utility Action Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onOpenLeaderboard,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Trophy,
                            contentDescription = "排行榜",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onOpenRules,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Book,
                            contentDescription = "规则说明",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Settings,
                            contentDescription = "设置",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Bottom Dashboard: Mine Counter | Face / Restart Button | Timer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Remaining Mines Badge
                StatBadge(
                    icon = AppIcons.Mine,
                    iconTint = MineDanger,
                    value = formatMines(remainingMines),
                    valueColor = MineDanger
                )

                // Face Status & Quick Restart Button (Yellow Smiley / Red Defeat Face)
                FilledTonalIconButton(
                    onClick = onRestart,
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = when (gameStatus) {
                            GameStatus.LOST -> Color(0x33EF4444)
                            GameStatus.WON -> Color(0x33F59E0B)
                            else -> MaterialTheme.colorScheme.secondaryContainer
                        }
                    )
                ) {
                    SmileyAvatar(
                        gameStatus = gameStatus,
                        size = 30.dp
                    )
                }

                // Timer Badge
                val minutes = elapsedSeconds / 60
                val seconds = elapsedSeconds % 60
                val formattedTime = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"

                StatBadge(
                    icon = AppIcons.Timer,
                    iconTint = Color(0xFF10B981),
                    value = formattedTime,
                    valueColor = Color(0xFF10B981)
                )
            }
        }
    }
}

private fun formatMines(value: Int): String {
    return if (value < 0) {
        "-${(-value).toString().padStart(2, '0')}"
    } else {
        value.toString().padStart(3, '0')
    }
}

@Composable
private fun StatBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    value: String,
    valueColor: Color
) {
    val isDark = LocalIsDark.current
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = iconTint
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = value,
                color = valueColor,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

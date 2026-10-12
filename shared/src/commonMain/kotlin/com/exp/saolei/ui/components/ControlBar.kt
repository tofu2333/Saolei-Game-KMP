package com.exp.saolei.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.model.GameInteractionMode
import com.exp.saolei.ui.theme.LocalIsDark
import com.exp.saolei.ui.theme.MineDanger
import com.exp.saolei.ui.theme.MinePrimary

@Composable
fun ControlBar(
    interactionMode: GameInteractionMode,
    onToggleMode: (GameInteractionMode) -> Unit,
    zoomScale: Float,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onResetView: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = LocalIsDark.current
) {
    val cardShape = RoundedCornerShape(22.dp)
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
        shape = cardShape,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mode Switcher Segmented Control
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1))
            ) {
                Row(
                    modifier = Modifier.padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dig Button
                    val isDig = interactionMode == GameInteractionMode.DIG
                    val digBg by animateColorAsState(
                        targetValue = if (isDig) MaterialTheme.colorScheme.primary else Color.Transparent,
                        animationSpec = tween(150)
                    )
                    val digContentColor by animateColorAsState(
                        targetValue = if (isDig) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(150)
                    )

                    Surface(
                        onClick = { onToggleMode(GameInteractionMode.DIG) },
                        shape = RoundedCornerShape(15.dp),
                        color = digBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = AppIcons.Shovel,
                                contentDescription = "挖雷",
                                modifier = Modifier.size(15.dp),
                                tint = digContentColor
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = "挖雷",
                                fontWeight = if (isDig) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.5.sp,
                                color = digContentColor
                            )
                        }
                    }

                    // Flag Button
                    val isFlag = interactionMode == GameInteractionMode.FLAG
                    val flagBg by animateColorAsState(
                        targetValue = if (isFlag) MineDanger else Color.Transparent,
                        animationSpec = tween(150)
                    )
                    val flagContentColor by animateColorAsState(
                        targetValue = if (isFlag) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(150)
                    )

                    Surface(
                        onClick = { onToggleMode(GameInteractionMode.FLAG) },
                        shape = RoundedCornerShape(15.dp),
                        color = flagBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = AppIcons.Flag,
                                contentDescription = "插旗",
                                modifier = Modifier.size(15.dp),
                                tint = flagContentColor
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = "插旗",
                                fontWeight = if (isFlag) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.5.sp,
                                color = flagContentColor
                            )
                        }
                    }
                }
            }

            // Zoom Control Stepper Capsule: [-] [100%] [+]
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1))
            ) {
                Row(
                    modifier = Modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Zoom Out [-]
                    IconButton(
                        onClick = onZoomOut,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Remove,
                            contentDescription = "缩小",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Percentage & Click to Reset to 100%
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onResetView() }
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${(zoomScale * 100).toInt()}%",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Zoom In [+]
                    IconButton(
                        onClick = onZoomIn,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Add,
                            contentDescription = "放大",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

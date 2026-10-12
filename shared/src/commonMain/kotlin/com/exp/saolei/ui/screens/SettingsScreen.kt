package com.exp.saolei.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.AppInfo
import com.exp.saolei.getPlatform
import com.exp.saolei.model.Cell
import com.exp.saolei.model.CellVisualState
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.GameSettings
import com.exp.saolei.model.ThemeMode
import com.exp.saolei.ui.components.AppIcons
import com.exp.saolei.ui.components.CellView
import com.exp.saolei.ui.theme.LocalIsDark
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    settings: GameSettings,
    onSettingsChanged: (GameSettings) -> Unit,
    onBack: () -> Unit,
    isDark: Boolean = LocalIsDark.current
) {
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = "返回",
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "游戏设置",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = { showResetConfirmDialog = true },
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Restart,
                        contentDescription = "恢复默认",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "恢复默认",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Group 1: 游戏机制与辅助
            item {
                SectionHeader("游戏机制与辅助")
            }

            item {
                val cardShape = RoundedCornerShape(16.dp)
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
                    shape = cardShape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SettingSwitchRow(
                            title = "首次点击绝对安全",
                            subtitle = "首次点击必非雷，开局舒适不暴毙",
                            checked = settings.firstClickSafe,
                            onCheckedChange = { onSettingsChanged(settings.copy(firstClickSafe = it)) }
                        )

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "首击必定展开 3×3 安全区",
                            subtitle = "首次点击周围九宫格必定为空白安全区",
                            checked = settings.firstClickEmpty3x3,
                            onCheckedChange = { onSettingsChanged(settings.copy(firstClickEmpty3x3 = it)) }
                        )

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "快速排雷 (双击/点击数字)",
                            subtitle = "已揭开的数字周围标旗数正确时，轻触自动排雷",
                            checked = settings.chordingEnabled,
                            onCheckedChange = { onSettingsChanged(settings.copy(chordingEnabled = it)) }
                        )

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "启用「?」疑惑标记",
                            subtitle = "标记状态循环：未翻开 ➔ 插旗 ➔ 疑惑 ➔ 未翻开",
                            checked = settings.questionMarkEnabled,
                            onCheckedChange = { onSettingsChanged(settings.copy(questionMarkEnabled = it)) }
                        )

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "通关时自动标记剩余地雷",
                            subtitle = "翻开所有安全格子后，自动将地雷处插上旗帜",
                            checked = settings.autoFlagOnWin,
                            onCheckedChange = { onSettingsChanged(settings.copy(autoFlagOnWin = it)) }
                        )
                    }
                }
            }

            // Group 2: 声音与触感
            item {
                SectionHeader("声音与触感")
            }

            item {
                val cardShape = RoundedCornerShape(16.dp)
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
                    shape = cardShape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SettingSwitchRow(
                            title = "游戏音效 (SFX)",
                            subtitle = "挖掘、插旗、排雷、引爆与通关音效",
                            checked = settings.sfxEnabled,
                            onCheckedChange = { onSettingsChanged(settings.copy(sfxEnabled = it)) }
                        )

                        if (settings.sfxEnabled) {
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("音效音量", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${(settings.sfxVolume * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Slider(
                                value = settings.sfxVolume,
                                onValueChange = { onSettingsChanged(settings.copy(sfxVolume = it)) },
                                valueRange = 0f..1f
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "背景音乐 (BGM)",
                            subtitle = "悠扬轻松的合成背景伴奏",
                            checked = settings.bgmEnabled,
                            onCheckedChange = { onSettingsChanged(settings.copy(bgmEnabled = it)) }
                        )

                        if (settings.bgmEnabled) {
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("背景音乐音量", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${(settings.bgmVolume * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Slider(
                                value = settings.bgmVolume,
                                onValueChange = { onSettingsChanged(settings.copy(bgmVolume = it)) },
                                valueRange = 0f..1f
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        SettingSwitchRow(
                            title = "触觉震动反馈",
                            subtitle = "在点击、插旗与排雷时提供清脆震动触感",
                            checked = settings.vibrationEnabled,
                            onCheckedChange = { onSettingsChanged(settings.copy(vibrationEnabled = it)) }
                        )
                    }
                }
            }

            // Group 3: 棋盘与显示
            item {
                SectionHeader("棋盘与显示")
            }

            item {
                val cardShape = RoundedCornerShape(16.dp)
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
                    shape = cardShape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "棋盘格子大小",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (settings.cellSize == 40) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Text(
                                    text = if (settings.cellSize == 40) "40 dp (默认)" else "${settings.cellSize} dp",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (settings.cellSize == 40) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "配置游戏棋盘方格基准尺寸（默认 40 dp），数值越大利于触控，数值越小单屏展现更广。",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        Spacer(Modifier.height(12.dp))

                        Slider(
                            value = settings.cellSize.toFloat(),
                            onValueChange = { onSettingsChanged(settings.copy(cellSize = it.roundToInt())) },
                            valueRange = 28f..60f,
                            steps = 15
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("紧凑 28dp", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("标准 40dp (默认)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                            Text("超大 60dp", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Spacer(Modifier.height(14.dp))

                        // Quick Presets
                        Text(
                            text = "快速预设",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(6.dp))

                        val presets = listOf(
                            Pair("紧凑\n32dp", 32),
                            Pair("标准 · 默认\n40dp", 40),
                            Pair("舒适\n48dp", 48),
                            Pair("大号\n56dp", 56)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            presets.forEach { (label, size) ->
                                val isSelected = settings.cellSize == size
                                Surface(
                                    onClick = { onSettingsChanged(settings.copy(cellSize = size)) },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHighest,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.primary else (if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1))
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 14.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // Live 3x3 Preview Container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9))
                                .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "当前尺寸效果预览 (3×3 样本)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                val previewCells = remember {
                                    listOf(
                                        listOf(
                                            Cell(row = 0, col = 0, visualState = CellVisualState.REVEALED, adjacentMines = 1),
                                            Cell(row = 0, col = 1, visualState = CellVisualState.REVEALED, adjacentMines = 2),
                                            Cell(row = 0, col = 2, visualState = CellVisualState.FLAGGED)
                                        ),
                                        listOf(
                                            Cell(row = 1, col = 0, visualState = CellVisualState.REVEALED, adjacentMines = 0),
                                            Cell(row = 1, col = 1, visualState = CellVisualState.HIDDEN),
                                            Cell(row = 1, col = 2, visualState = CellVisualState.HIDDEN)
                                        ),
                                        listOf(
                                            Cell(row = 2, col = 0, visualState = CellVisualState.HIDDEN),
                                            Cell(row = 2, col = 1, visualState = CellVisualState.HIDDEN),
                                            Cell(row = 2, col = 2, visualState = CellVisualState.HIDDEN)
                                        )
                                    )
                                }

                                val previewCellSize = settings.cellSize.dp
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    for (r in 0 until 3) {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            for (c in 0 until 3) {
                                                CellView(
                                                    cell = previewCells[r][c],
                                                    size = previewCellSize,
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

            // Group 4: 外观与主题
            item {
                SectionHeader("外观与主题")
            }

            item {
                val cardShape = RoundedCornerShape(16.dp)
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
                    shape = cardShape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "色彩模式",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(10.dp))

                        ThemeModeSegmentedControl(
                            selectedMode = settings.themeMode,
                            onModeSelected = { newMode ->
                                onSettingsChanged(settings.copy(themeMode = newMode))
                            }
                        )
                    }
                }
            }

            // Group 4: 关于与版本
            item {
                SectionHeader("关于与版本")
            }

            item {
                val cardShape = RoundedCornerShape(16.dp)
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1), cardShape),
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
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "应用名称",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = AppInfo.APP_NAME,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "当前版本",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = AppInfo.FULL_VERSION,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "运行平台",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = getPlatform().name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
    }

    // Reset Confirmation Dialog
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = {
                Text(
                    text = "恢复默认设置",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "确定要将所有游戏设置（包括棋盘格子大小、辅助排雷、声音与色彩主题）恢复为默认配置吗？",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSettingsChanged(GameSettings())
                        showResetConfirmDialog = false
                    }
                ) {
                    Text("确定恢复")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showResetConfirmDialog = false }
                ) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 4.dp, top = 8.dp)
    )
}

@Composable
private fun SettingSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun ThemeModeSegmentedControl(
    selectedMode: ThemeMode,
    onModeSelected: (ThemeMode) -> Unit
) {
    val isDark = LocalIsDark.current
    val modes = listOf(
        Triple(ThemeMode.SYSTEM, "跟随系统", AppIcons.AutoMode),
        Triple(ThemeMode.LIGHT, "浅色模式", AppIcons.Sun),
        Triple(ThemeMode.DARK, "深色模式", AppIcons.Moon)
    )

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            modes.forEach { (mode, label, icon) ->
                val isSelected = selectedMode == mode

                val targetBg = if (isSelected) {
                    MaterialTheme.colorScheme.surface
                } else {
                    Color.Transparent
                }
                val bgColor by animateColorAsState(targetBg, animationSpec = tween(180))

                val targetContentColor = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
                val contentColor by animateColorAsState(targetContentColor, animationSpec = tween(180))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = bgColor,
                    border = if (isSelected) BorderStroke(1.dp, if (isDark) Color(0xFF475569) else Color(0xFFCBD5E1)) else null,
                    shadowElevation = if (isSelected) 2.dp else 0.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onModeSelected(mode)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = contentColor
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}

package com.exp.saolei.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.model.DifficultyType
import com.exp.saolei.model.GameRecord
import com.exp.saolei.storage.DataManager
import com.exp.saolei.ui.components.AppIcons
import com.exp.saolei.ui.theme.LocalIsDark
import com.exp.saolei.ui.theme.MinePrimary

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun LeaderboardScreen(
    onBack: () -> Unit,
    isDark: Boolean = LocalIsDark.current
) {
    var selectedDifficulty by remember { mutableStateOf(DifficultyType.EASY) }
    var refreshTrigger by remember { mutableStateOf(0) }
    var showClearDialog by remember { mutableStateOf(false) }

    val records = remember(selectedDifficulty, refreshTrigger) {
        DataManager.getRecords(selectedDifficulty)
    }

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                        text = "通关排行榜",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            if (records.isNotEmpty()) {
                TextButton(onClick = { showClearDialog = true }) {
                    Text(
                        text = "清空记录",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // Difficulty Tabs
        val tabs = listOf(
            DifficultyType.EASY to "初级 (9×9)",
            DifficultyType.MEDIUM to "中级 (16×16)",
            DifficultyType.HARD to "高级 (16×30)",
            DifficultyType.CUSTOM to "自定义"
        )
        val selectedIndex = tabs.indexOfFirst { it.first == selectedDifficulty }.coerceAtLeast(0)

        PrimaryTabRow(
            selectedTabIndex = selectedIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)), RoundedCornerShape(12.dp))
        ) {
            tabs.forEachIndexed { index, (diffType, label) ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = { selectedDifficulty = diffType },
                    text = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        // Records List (Top 20)
        if (records.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = AppIcons.Trophy,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = "暂无通关记录",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "完成对局后，最佳成绩将记录于此",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "仅保留前 20 次最短用时记录",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                    )
                }

                itemsIndexed(records) { index, record ->
                    LeaderboardItem(
                        rank = index + 1,
                        record = record,
                        isDark = isDark
                    )
                }

                item {
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("清空排行榜记录") },
            text = { Text("确定要清空该难度的所有本地通关记录吗？此操作无法撤销。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        DataManager.clearRecords(selectedDifficulty)
                        refreshTrigger++
                        showClearDialog = false
                    }
                ) {
                    Text("确认清空", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun LeaderboardItem(
    rank: Int,
    record: GameRecord,
    isDark: Boolean
) {
    val isTop3 = rank <= 3

    val rankContainerColor = when (rank) {
        1 -> if (isDark) Color(0xFF78350F) else Color(0xFFFEF3C7)
        2 -> if (isDark) Color(0xFF334155) else Color(0xFFF1F5F9)
        3 -> if (isDark) Color(0xFF7C2D12) else Color(0xFFFFEDD5)
        else -> MaterialTheme.colorScheme.surfaceContainerHighest
    }

    val rankContentColor = when (rank) {
        1 -> if (isDark) Color(0xFFFDE68A) else Color(0xFFD97706)
        2 -> if (isDark) Color(0xFFE2E8F0) else Color(0xFF475569)
        3 -> if (isDark) Color(0xFFFED7AA) else Color(0xFFEA580C)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = rankContainerColor,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$rank",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = rankContentColor
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        text = record.formattedTime,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = if (rank == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "点击 ${record.clicks} 次 · 插旗 ${record.flagsPlaced} 枚",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (record.dateFormatted.isNotBlank()) {
                Text(
                    text = record.dateFormatted,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

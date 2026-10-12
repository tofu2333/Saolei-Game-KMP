package com.exp.saolei.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.exp.saolei.model.Difficulty
import com.exp.saolei.ui.theme.LocalIsDark

@Composable
fun CustomGameDialog(
    initialRows: Int = 12,
    initialCols: Int = 12,
    initialMines: Int = 20,
    onConfirm: (Difficulty) -> Unit,
    onDismiss: () -> Unit,
    isDark: Boolean = LocalIsDark.current
) {
    var rows by remember { mutableFloatStateOf(initialRows.toFloat()) }
    var cols by remember { mutableFloatStateOf(initialCols.toFloat()) }
    var mines by remember { mutableFloatStateOf(initialMines.toFloat()) }

    val currentRows = rows.toInt()
    val currentCols = cols.toInt()
    val totalCells = currentRows * currentCols
    val maxMines = (totalCells - 9).coerceAtLeast(1)
    val safeMines = mines.toInt().coerceIn(1, maxMines)

    val density = if (totalCells > 0) (safeMines.toDouble() / totalCells * 100) else 0.0

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)),
            shadowElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = AppIcons.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "自定义游戏难度",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(Modifier.height(18.dp))

                // Rows Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "棋盘行数",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$currentRows 行",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = rows,
                    onValueChange = {
                        rows = it
                        if (mines > (it.toInt() * currentCols - 9)) {
                            mines = (it.toInt() * currentCols - 9).toFloat().coerceAtLeast(1f)
                        }
                    },
                    valueRange = 8f..32f,
                    steps = 23
                )

                Spacer(Modifier.height(10.dp))

                // Cols Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "棋盘列数",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$currentCols 列",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = cols,
                    onValueChange = {
                        cols = it
                        if (mines > (currentRows * it.toInt() - 9)) {
                            mines = (currentRows * it.toInt() - 9).toFloat().coerceAtLeast(1f)
                        }
                    },
                    valueRange = 8f..30f,
                    steps = 21
                )

                Spacer(Modifier.height(10.dp))

                // Mines Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "地雷总数",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$safeMines 颗 (${density.toInt()}%)",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Slider(
                    value = safeMines.toFloat(),
                    onValueChange = { mines = it },
                    valueRange = 1f..maxMines.toFloat()
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("取消")
                    }
                    Button(
                        onClick = {
                            val customDiff = Difficulty.custom(currentRows, currentCols, safeMines)
                            onConfirm(customDiff)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("开始游戏")
                    }
                }
            }
        }
    }
}

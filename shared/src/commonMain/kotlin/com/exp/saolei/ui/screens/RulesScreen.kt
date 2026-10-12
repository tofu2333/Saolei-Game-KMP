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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.ui.components.AppIcons
import com.exp.saolei.ui.theme.LocalIsDark

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun RulesScreen(
    onBack: () -> Unit,
    isDark: Boolean = LocalIsDark.current
) {
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
                    text = "游戏规则与攻略",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                RuleCard(
                    icon = AppIcons.Target,
                    title = "游戏核心目标",
                    content = "在不触雷的前提下，翻开棋盘上所有的非雷格子。只要所有安全的格子都被成功翻开，游戏即宣告胜利。"
                )
            }

            item {
                RuleCard(
                    icon = AppIcons.Mine,
                    title = "数字的含义",
                    content = "每个被翻开的数字代表它周围 8 个相邻格子中隐藏的地雷总数（1 到 8）。例如数字「1」表示周围 8 格中有且仅有 1 颗雷；空白格子表示周围 0 颗雷，并会自动展开整片安全区域。"
                )
            }

            item {
                RuleCard(
                    icon = AppIcons.Shovel,
                    title = "挖雷与插旗操作",
                    content = "底部的模式切换胶囊按钮可轻松切换【挖雷】与【插旗】模式。\n• 挖雷模式：轻触翻开格子，长按可快速插旗标雷。\n• 插旗模式：轻触插旗或标记疑惑状态（?），长按可翻开格子。\n• 桌面端支持鼠标左键挖雷、右键插旗。"
                )
            }

            item {
                RuleCard(
                    icon = AppIcons.Lightning,
                    title = "双击排雷 (Chording)",
                    content = "当已揭开的数字周围所标旗帜数量等于该数字时，轻触该数字格子，系统会自动翻开周围剩余的所有未插旗格子。熟练运用此技巧可大幅缩短通关用时。"
                )
            }

            item {
                RuleCard(
                    icon = AppIcons.Shield,
                    title = "首次点击安全机制",
                    content = "游戏默认开启「首次点击安全」。第一下点击的位置绝对不是雷，并且会自动生成大片舒适空白开局，避免开局直接触雷。"
                )
            }

            item {
                RuleCard(
                    icon = AppIcons.Book,
                    title = "经典排雷定式",
                    content = "• 1-2-1 定式：连续三个数字呈 1-2-1 排列时，两端的「1」外侧必是地雷，中间的「2」正前方必安全。\n• 1-1 定式：在平直边缘遇到连续两个「1」，第二个「1」延伸的第三格外侧必是安全格。\n• 角落定式：凸出直角尖端的「1」，其唯一对角的斜向格子必定是雷。"
                )
            }

            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
    }
}

@Composable
private fun RuleCard(
    icon: ImageVector,
    title: String,
    content: String,
    isDark: Boolean = LocalIsDark.current
) {
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
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

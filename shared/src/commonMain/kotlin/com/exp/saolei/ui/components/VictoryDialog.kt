package com.exp.saolei.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.exp.saolei.model.Difficulty
import com.exp.saolei.model.VictoryDialogStyle

@Composable
fun VictoryDialog(
    style: VictoryDialogStyle,
    timeSeconds: Double,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val formattedTime = remember(timeSeconds) {
        val totalSec = timeSeconds.toInt()
        val minutes = totalSec / 60
        val seconds = totalSec % 60
        val millis = ((timeSeconds - totalSec) * 10).toInt().coerceIn(0, 9)
        if (minutes > 0) {
            "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}.${millis}"
        } else {
            "${seconds}.${millis}s"
        }
    }

    Dialog(onDismissRequest = onReviewBoard) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (style) {
                VictoryDialogStyle.CYBERPUNK -> CyberpunkVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.RETRO_PIXEL -> RetroPixelVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.ROYAL_GOLD -> RoyalGoldVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.MINIMAL_GLASS -> MinimalGlassVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.CARNIVAL_PARTY -> CarnivalPartyVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.COSMIC_GALAXY -> CosmicGalaxyVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.EMERALD_NATURE -> EmeraldNatureVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.STEAMPUNK_CLOCKWORK -> SteampunkVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.SWEET_MACARON -> SweetMacaronVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )

                VictoryDialogStyle.SECRET_AGENT -> SecretAgentVictoryStyle(
                    time = formattedTime,
                    difficulty = difficulty,
                    clicks = clicks,
                    flags = flags,
                    isNewRecord = isNewRecord,
                    rank = rank,
                    onPlayAgain = onPlayAgain,
                    onReviewBoard = onReviewBoard,
                    onViewLeaderboard = onViewLeaderboard
                )
            }
        }
    }
}

// -------------------------------------------------------------
// Style 1: 赛博朋克·霓虹幻境 (Cyberpunk Neon)
// -------------------------------------------------------------
@Composable
fun CyberpunkVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val neonCyan = Color(0xFF00F0FF)
    val neonPink = Color(0xFFFF007F)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Brush.linearGradient(listOf(neonCyan, neonPink)), shape),
        shape = shape,
        color = Color(0xFF070B19),
        tonalElevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "PROTOCOL // CLEARED",
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = neonCyan
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "DECRYPTED",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 26.sp,
                color = neonCyan
            )

            Text(
                text = "防线突破 · 扫雷大捷",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF0F172A),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("CLEARANCE TIME", fontSize = 10.sp, color = neonPink, fontFamily = FontFamily.Monospace)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = time,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = neonCyan)
            Spacer(Modifier.height(20.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = neonCyan,
                textColor = Color.Black
            )
        }
    }
}

// -------------------------------------------------------------
// Style 2: 复古像素·8-Bit街机 (Retro 8-Bit Arcade)
// -------------------------------------------------------------
@Composable
fun RetroPixelVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val pixelGold = Color(0xFFFFD700)
    val pixelGreen = Color(0xFF4ADE80)
    val shape = RoundedCornerShape(8.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, pixelGold, shape),
        shape = shape,
        color = Color(0xFF181425)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text("ARCADE 1989", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = pixelGold)

            Spacer(Modifier.height(16.dp))

            Icon(
                imageVector = AppIcons.Trophy,
                contentDescription = null,
                tint = pixelGold,
                modifier = Modifier.size(36.dp)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "STAGE CLEAR",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                color = pixelGold
            )
            Text(
                text = "HIGH SCORE ACHIEVED",
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = pixelGreen
            )

            Spacer(Modifier.height(14.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF261D42),
                shape = shape
            ) {
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "TIME: $time",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = pixelGold)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = pixelGold,
                textColor = Color(0xFF181425)
            )
        }
    }
}

// -------------------------------------------------------------
// Style 3: 荣耀加冕·极光流金 (Royal Gold Aurora)
// -------------------------------------------------------------
@Composable
fun RoyalGoldVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val goldPrimary = Color(0xFFF59E0B)
    val goldLight = Color(0xFFFDE68A)
    val shape = RoundedCornerShape(24.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Brush.linearGradient(listOf(goldLight, goldPrimary)), shape),
        shape = shape,
        color = Color(0xFF0F172A),
        tonalElevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text("ROYAL CHAMPION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = goldLight)

            Spacer(Modifier.height(14.dp))

            Icon(
                imageVector = AppIcons.Trophy,
                contentDescription = null,
                tint = goldPrimary,
                modifier = Modifier.size(38.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "荣耀加冕 · 扫雷王者",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = goldLight
            )
            Text(
                text = "智勇双全，弹指排净万雷",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0x22F59E0B),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("破阵用时", style = MaterialTheme.typography.labelSmall, color = goldLight)
                    Text(
                        text = time,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = goldPrimary)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = goldPrimary,
                textColor = Color(0xFF0F172A)
            )
        }
    }
}

// -------------------------------------------------------------
// Style 4: 极简禅意·晨雾拟态 (Minimalist Glassmorphism)
// -------------------------------------------------------------
@Composable
fun MinimalGlassVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    val accent = MaterialTheme.colorScheme.primary

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "ZEN HARMONY",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "心明镜澈 · 扫雷达成",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "从容不迫，步步为营",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = time,
                fontSize = 36.sp,
                fontWeight = FontWeight.Light,
                color = accent
            )

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = MaterialTheme.colorScheme.onSurface)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = accent)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = accent,
                textColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

// -------------------------------------------------------------
// Style 5: 盛大狂欢·缤纷礼花 (Carnival Party)
// -------------------------------------------------------------
@Composable
fun CarnivalPartyVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val partyYellow = Color(0xFFFBBF24)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, partyYellow, shape),
        shape = shape,
        color = Color(0xFF1C1917)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text("CARNIVAL CELEBRATION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = partyYellow)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "盛大狂欢 · 欢庆胜利",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "全场焦点，完美排除全部雷区！",
                style = MaterialTheme.typography.bodySmall,
                color = partyYellow
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF292524),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = time,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = partyYellow
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = partyYellow)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = partyYellow,
                textColor = Color(0xFF1C1917)
            )
        }
    }
}

// -------------------------------------------------------------
// Style 6: 浩瀚星河·深空漫游 (Cosmic Galaxy Explorer)
// -------------------------------------------------------------
@Composable
fun CosmicGalaxyVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(22.dp)
    val cosmicViolet = Color(0xFFA855F7)
    val starCyan = Color(0xFF38BDF8)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Brush.linearGradient(listOf(cosmicViolet, starCyan)), shape),
        shape = shape,
        color = Color(0xFF090D1A)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text("COSMIC MISSION", fontSize = 11.sp, color = starCyan, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "星河漫游 · 险区排尽",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "引力雷区已全数清除",
                style = MaterialTheme.typography.bodySmall,
                color = starCyan
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = time,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = cosmicViolet
            )

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = cosmicViolet)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = cosmicViolet,
                textColor = Color.White
            )
        }
    }
}

// -------------------------------------------------------------
// Style 7: 翡翠秘境·自然之语 (Emerald Nature Zen)
// -------------------------------------------------------------
@Composable
fun EmeraldNatureVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val emerald = Color(0xFF10B981)
    val mint = Color(0xFFA7F3D0)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, emerald, shape),
        shape = shape,
        color = Color(0xFF022C22)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text("EMERALD SANCTUARY", fontSize = 11.sp, color = mint, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "翡翠秘境 · 归于宁静",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = mint
            )
            Text(
                text = "森林恢复祥和，迷雾全数消散",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = time,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = emerald
            )

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = emerald)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = emerald,
                textColor = Color(0xFF022C22)
            )
        }
    }
}

// -------------------------------------------------------------
// Style 8: 蒸汽朋克·精密黄铜 (Steampunk Clockwork)
// -------------------------------------------------------------
@Composable
fun SteampunkVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val brass = Color(0xFFD97706)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, brass, shape),
        shape = shape,
        color = Color(0xFF1C1917)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text("CHRONO GEAR INDUSTRIAL", fontSize = 11.sp, color = brass, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "精密齿轮 · 引信拆除",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = brass
            )
            Text(
                text = "蒸汽压力归零，机械机巧安全停转",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFFDE68A)
            )

            Spacer(Modifier.height(14.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF291508),
                shape = shape
            ) {
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "TIME: $time",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = brass)
            Spacer(Modifier.height(16.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = brass,
                textColor = Color(0xFF1C1917)
            )
        }
    }
}

// -------------------------------------------------------------
// Style 9: 甜心派对·马卡龙波普 (Sweet Pop Macaron)
// -------------------------------------------------------------
@Composable
fun SweetMacaronVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    val macaronPink = Color(0xFFF472B6)
    val macaronBlue = Color(0xFF60A5FA)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Brush.horizontalGradient(listOf(macaronPink, macaronBlue)), shape),
        shape = shape,
        color = Color(0xFF1E1B4B)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(22.dp)
        ) {
            Text("SWEET POP", fontSize = 11.sp, color = macaronPink, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "甜心派对 · 胜利通关",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "游刃有余，轻巧化险为夷",
                style = MaterialTheme.typography.bodySmall,
                color = macaronPink
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = time,
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                color = macaronBlue
            )

            Spacer(Modifier.height(14.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = macaronPink)
            Spacer(Modifier.height(18.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = macaronPink,
                textColor = Color.White
            )
        }
    }
}

// -------------------------------------------------------------
// Style 10: 绝密特工·谍影重重 (Secret Agent Classified)
// -------------------------------------------------------------
@Composable
fun SecretAgentVictoryStyle(
    time: String,
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    isNewRecord: Boolean,
    rank: Int?,
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val stampRed = Color(0xFFDC2626)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF78716C), shape),
        shape = shape,
        color = Color(0xFF292524)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text("TOP SECRET // DOSSIER", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFFA8A29E))

            Spacer(Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .rotate(-4f)
                    .border(2.5.dp, stampRed, RoundedCornerShape(4.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "PASSED // 任务达成",
                    color = stampRed,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 17.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "机密档案解密完成",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFFD6D3D1)
            )

            Spacer(Modifier.height(14.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1C1917),
                shape = RoundedCornerShape(6.dp)
            ) {
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "CLEARANCE: $time",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            StatsRow(difficulty = difficulty, clicks = clicks, flags = flags, textColor = Color.White)
            RankBadges(isNewRecord = isNewRecord, rank = rank, accentColor = stampRed)
            Spacer(Modifier.height(16.dp))

            ActionButtonsRow(
                onPlayAgain = onPlayAgain,
                onReviewBoard = onReviewBoard,
                onViewLeaderboard = onViewLeaderboard,
                primaryColor = stampRed,
                textColor = Color.White
            )
        }
    }
}

// -------------------------------------------------------------
// Common Reusable Components in Victory Dialogs
// -------------------------------------------------------------
@Composable
private fun StatsRow(
    difficulty: Difficulty,
    clicks: Int,
    flags: Int,
    textColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0x18888888),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("难度", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(difficulty.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = textColor)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("步数", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text("$clicks 次", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = textColor)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("排雷", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text("${difficulty.mines} 枚", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = textColor)
            }
        }
    }
}

@Composable
private fun RankBadges(
    isNewRecord: Boolean,
    rank: Int?,
    accentColor: Color
) {
    if (isNewRecord || rank != null) {
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isNewRecord) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor
                ) {
                    Text(
                        text = "NEW RECORD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
            }
            if (rank != null) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF334155)
                ) {
                    Text(
                        text = "第 $rank 名",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButtonsRow(
    onPlayAgain: () -> Unit,
    onReviewBoard: () -> Unit,
    onViewLeaderboard: () -> Unit,
    primaryColor: Color,
    textColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = textColor
            )
        ) {
            Text("再来一局", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onReviewBoard,
                modifier = Modifier.weight(1f).height(40.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("复盘棋盘", fontSize = 12.sp)
            }
            OutlinedButton(
                onClick = onViewLeaderboard,
                modifier = Modifier.weight(1f).height(40.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("排行榜", fontSize = 12.sp)
            }
        }
    }
}

package com.exp.saolei.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exp.saolei.model.GameStatus

@Composable
fun SmileyAvatar(
    gameStatus: GameStatus,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    val emoji = when (gameStatus) {
        GameStatus.LOST -> "😵"
        GameStatus.WON -> "😎"
        else -> "🙂"
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = (size.value * 0.70f).sp,
            textAlign = TextAlign.Center
        )
    }
}

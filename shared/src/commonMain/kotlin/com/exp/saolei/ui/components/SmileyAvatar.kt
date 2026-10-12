package com.exp.saolei.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exp.saolei.model.GameStatus

@Composable
fun SmileyAvatar(
    gameStatus: GameStatus,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)
        val radius = w * 0.44f

        when (gameStatus) {
            GameStatus.LOST -> {
                // 踩雷后：红脸 (Angry / Shocked / Defeated Red Face)
                drawRedFace(center, radius)
            }
            GameStatus.WON -> {
                // 获胜后：墨镜酷酷笑脸 (Cool Victorious Sunglasses Face)
                drawWonFace(center, radius)
            }
            else -> {
                // 游戏中 / 初始状态：经典黄色笑脸 (Classic Happy Yellow Face)
                drawHappyYellowFace(center, radius)
            }
        }
    }
}

/**
 * 经典黄色笑脸 (Classic Happy Yellow Face)
 */
private fun DrawScope.drawHappyYellowFace(center: Offset, radius: Float) {
    // 渐变黄脸底盘
    val faceBrush = Brush.radialGradient(
        colors = listOf(Color(0xFFFFEE58), Color(0xFFF59E0B)),
        center = Offset(center.x - radius * 0.2f, center.y - radius * 0.25f),
        radius = radius * 1.3f
    )
    drawCircle(
        brush = faceBrush,
        radius = radius,
        center = center
    )
    // 细致边缘描边
    drawCircle(
        color = Color(0xFFD97706),
        radius = radius,
        center = center,
        style = Stroke(width = radius * 0.08f)
    )

    // 柔和微粉腮红
    val blushRadius = radius * 0.18f
    val blushColor = Color(0x35FF4081)
    drawCircle(
        color = blushColor,
        radius = blushRadius,
        center = Offset(center.x - radius * 0.52f, center.y + radius * 0.16f)
    )
    drawCircle(
        color = blushColor,
        radius = blushRadius,
        center = Offset(center.x + radius * 0.52f, center.y + radius * 0.16f)
    )

    // 两只黑色椭圆大眼睛
    val eyeColor = Color(0xFF1E293B)
    val eyeW = radius * 0.16f
    val eyeH = radius * 0.25f
    val eyeY = center.y - radius * 0.16f

    // 左眼
    drawOval(
        color = eyeColor,
        topLeft = Offset(center.x - radius * 0.38f - eyeW / 2f, eyeY - eyeH / 2f),
        size = Size(eyeW, eyeH)
    )
    // 右眼
    drawOval(
        color = eyeColor,
        topLeft = Offset(center.x + radius * 0.38f - eyeW / 2f, eyeY - eyeH / 2f),
        size = Size(eyeW, eyeH)
    )

    // 眼睛白色高光点 (卡哇伊 twinkle)
    val shineRadius = radius * 0.05f
    drawCircle(
        color = Color.White,
        radius = shineRadius,
        center = Offset(center.x - radius * 0.40f, eyeY - eyeH * 0.25f)
    )
    drawCircle(
        color = Color.White,
        radius = shineRadius,
        center = Offset(center.x + radius * 0.36f, eyeY - eyeH * 0.25f)
    )

    // 温暖向上的弧形笑嘴
    val mouthPath = Path().apply {
        moveTo(center.x - radius * 0.44f, center.y + radius * 0.18f)
        quadraticTo(
            center.x, center.y + radius * 0.65f,
            center.x + radius * 0.44f, center.y + radius * 0.18f
        )
    }
    drawPath(
        path = mouthPath,
        color = eyeColor,
        style = Stroke(
            width = radius * 0.13f,
            cap = StrokeCap.Round
        )
    )
}

/**
 * 踩雷后红脸 (Red Angry / Defeated Face)
 */
private fun DrawScope.drawRedFace(center: Offset, radius: Float) {
    // 灼热渐变红脸底盘
    val faceBrush = Brush.radialGradient(
        colors = listOf(Color(0xFFF87171), Color(0xFFDC2626)),
        center = Offset(center.x - radius * 0.2f, center.y - radius * 0.25f),
        radius = radius * 1.3f
    )
    drawCircle(
        brush = faceBrush,
        radius = radius,
        center = center
    )
    // 深红轮廓描边
    drawCircle(
        color = Color(0xFF991B1B),
        radius = radius,
        center = center,
        style = Stroke(width = radius * 0.08f)
    )

    val darkFeatureColor = Color(0xFF1E293B)
    val strokeWidth = radius * 0.12f

    // 愤怒紧锁的双眉 (提升至额头上方，与眼睛完全分离，绝不互相重叠或相连)
    val browStrokeWidth = radius * 0.085f
    val leftBrowPath = Path().apply {
        moveTo(center.x - radius * 0.52f, center.y - radius * 0.50f)
        lineTo(center.x - radius * 0.18f, center.y - radius * 0.38f)
    }
    val rightBrowPath = Path().apply {
        moveTo(center.x + radius * 0.52f, center.y - radius * 0.50f)
        lineTo(center.x + radius * 0.18f, center.y - radius * 0.38f)
    }
    drawPath(leftBrowPath, darkFeatureColor, style = Stroke(browStrokeWidth, cap = StrokeCap.Round))
    drawPath(rightBrowPath, darkFeatureColor, style = Stroke(browStrokeWidth, cap = StrokeCap.Round))

    // 触雷眩晕 "X" 眼睛 (规整十字旋转45度，圆润端点，独立清晰居中)
    val crossSize = radius * 0.15f
    val eyeY = center.y - radius * 0.12f

    // 左眼 X
    val leftEyeCenter = Offset(center.x - radius * 0.36f, eyeY)
    drawLine(
        color = darkFeatureColor,
        start = Offset(leftEyeCenter.x - crossSize, leftEyeCenter.y - crossSize),
        end = Offset(leftEyeCenter.x + crossSize, leftEyeCenter.y + crossSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    drawLine(
        color = darkFeatureColor,
        start = Offset(leftEyeCenter.x - crossSize, leftEyeCenter.y + crossSize),
        end = Offset(leftEyeCenter.x + crossSize, leftEyeCenter.y - crossSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    // 右眼 X
    val rightEyeCenter = Offset(center.x + radius * 0.36f, eyeY)
    drawLine(
        color = darkFeatureColor,
        start = Offset(rightEyeCenter.x - crossSize, rightEyeCenter.y - crossSize),
        end = Offset(rightEyeCenter.x + crossSize, rightEyeCenter.y + crossSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    drawLine(
        color = darkFeatureColor,
        start = Offset(rightEyeCenter.x - crossSize, rightEyeCenter.y + crossSize),
        end = Offset(rightEyeCenter.x + crossSize, rightEyeCenter.y - crossSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    // 下撇的气恼/委屈悲伤大嘴角
    val mouthPath = Path().apply {
        moveTo(center.x - radius * 0.44f, center.y + radius * 0.48f)
        quadraticTo(
            center.x, center.y + radius * 0.20f,
            center.x + radius * 0.44f, center.y + radius * 0.48f
        )
    }
    drawPath(
        path = mouthPath,
        color = darkFeatureColor,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

/**
 * 通关胜利墨镜笑脸 (Cool Victorious Sunglasses Face)
 */
private fun DrawScope.drawWonFace(center: Offset, radius: Float) {
    // 耀眼金黄渐变
    val faceBrush = Brush.radialGradient(
        colors = listOf(Color(0xFFFEF08A), Color(0xFFF59E0B)),
        center = Offset(center.x - radius * 0.2f, center.y - radius * 0.25f),
        radius = radius * 1.3f
    )
    drawCircle(brush = faceBrush, radius = radius, center = center)
    drawCircle(color = Color(0xFFB45309), radius = radius, center = center, style = Stroke(radius * 0.08f))

    // 酷炫墨镜
    val glassesColor = Color(0xFF0F172A)
    val glassesTop = center.y - radius * 0.36f
    val glassesBottom = center.y + radius * 0.08f

    val glassesPath = Path().apply {
        // 左镜框
        moveTo(center.x - radius * 0.65f, glassesTop)
        lineTo(center.x - radius * 0.06f, glassesTop)
        lineTo(center.x - radius * 0.10f, glassesBottom)
        quadraticTo(center.x - radius * 0.40f, glassesBottom + radius * 0.08f, center.x - radius * 0.65f, glassesBottom)
        close()

        // 右镜框
        moveTo(center.x + radius * 0.06f, glassesTop)
        lineTo(center.x + radius * 0.65f, glassesTop)
        lineTo(center.x + radius * 0.65f, glassesBottom)
        quadraticTo(center.x + radius * 0.40f, glassesBottom + radius * 0.08f, center.x + radius * 0.10f, glassesBottom)
        close()
    }
    drawPath(glassesPath, color = glassesColor, style = Fill)

    // 墨镜横梁
    drawLine(
        color = glassesColor,
        start = Offset(center.x - radius * 0.70f, glassesTop + radius * 0.04f),
        end = Offset(center.x + radius * 0.70f, glassesTop + radius * 0.04f),
        strokeWidth = radius * 0.10f,
        cap = StrokeCap.Round
    )

    // 墨镜白色反光弧线
    drawLine(
        color = Color(0x66FFFFFF),
        start = Offset(center.x - radius * 0.50f, glassesTop + radius * 0.08f),
        end = Offset(center.x - radius * 0.25f, glassesBottom - radius * 0.04f),
        strokeWidth = radius * 0.06f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = Color(0x66FFFFFF),
        start = Offset(center.x + radius * 0.22f, glassesTop + radius * 0.08f),
        end = Offset(center.x + radius * 0.47f, glassesBottom - radius * 0.04f),
        strokeWidth = radius * 0.06f,
        cap = StrokeCap.Round
    )

    // 灿烂胜利露齿大笑嘴
    val smilePath = Path().apply {
        moveTo(center.x - radius * 0.44f, center.y + radius * 0.32f)
        quadraticTo(
            center.x, center.y + radius * 0.70f,
            center.x + radius * 0.44f, center.y + radius * 0.32f
        )
    }
    drawPath(smilePath, color = Color(0xFF1E293B), style = Stroke(radius * 0.13f, cap = StrokeCap.Round))
}

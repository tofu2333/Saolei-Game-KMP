package com.exp.saolei.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {
    val Shovel: ImageVector by lazy {
        ImageVector.Builder(
            name = "Shovel",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                // Blade
                moveTo(14.7f, 2.3f)
                lineTo(21.7f, 9.3f)
                lineTo(18.2f, 12.8f)
                lineTo(15.4f, 10f)
                lineTo(12.6f, 12.8f)
                lineTo(9.8f, 10f)
                lineTo(12.6f, 7.2f)
                lineTo(9.8f, 4.4f)
                close()
                // Handle shaft
                moveTo(8.4f, 11.4f)
                lineTo(12.6f, 15.6f)
                lineTo(4.2f, 24f)
                lineTo(0f, 19.8f)
                close()
            }
        }.build()
    }

    val Flag: ImageVector by lazy {
        ImageVector.Builder(
            name = "Flag",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(14.4f, 6f)
                lineTo(14f, 4f)
                horizontalLineTo(5f)
                verticalLineTo(21f)
                horizontalLineTo(7f)
                verticalLineTo(14f)
                horizontalLineTo(12.6f)
                lineTo(13f, 16f)
                horizontalLineTo(20f)
                verticalLineTo(6f)
                horizontalLineTo(14.4f)
                close()
            }
        }.build()
    }

    val Mine: ImageVector get() = Settings

    val Timer: ImageVector by lazy {
        ImageVector.Builder(
            name = "Timer",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(15f, 1f)
                horizontalLineTo(9f)
                verticalLineTo(3f)
                horizontalLineTo(15f)
                verticalLineTo(1f)
                close()
                moveTo(11f, 14f)
                horizontalLineTo(13f)
                verticalLineTo(8f)
                horizontalLineTo(11f)
                verticalLineTo(14f)
                close()
                moveTo(19.03f, 7.39f)
                lineTo(20.45f, 5.97f)
                curveTo(20.02f, 5.46f, 19.55f, 4.98f, 19.04f, 4.56f)
                lineTo(17.62f, 5.98f)
                curveTo(16.07f, 4.74f, 14.12f, 4f, 12f, 4f)
                curveTo(7.03f, 4f, 3f, 8.03f, 3f, 13f)
                curveTo(3f, 17.97f, 7.02f, 22f, 12f, 22f)
                curveTo(16.98f, 22f, 21f, 17.97f, 21f, 13f)
                curveTo(21f, 10.88f, 20.26f, 8.93f, 19.03f, 7.39f)
                close()
                moveTo(12f, 20f)
                curveTo(8.13f, 20f, 5f, 16.87f, 5f, 13f)
                curveTo(5f, 9.13f, 8.13f, 6f, 12f, 6f)
                curveTo(15.87f, 6f, 19f, 9.13f, 19f, 13f)
                curveTo(19f, 16.87f, 15.87f, 20f, 12f, 20f)
                close()
            }
        }.build()
    }

    val Trophy: ImageVector by lazy {
        ImageVector.Builder(
            name = "Trophy",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(19f, 5f)
                horizontalLineTo(17f)
                verticalLineTo(3f)
                horizontalLineTo(7f)
                verticalLineTo(5f)
                horizontalLineTo(5f)
                curveTo(3.9f, 5f, 3f, 5.9f, 3f, 7f)
                verticalLineTo(9f)
                curveTo(3f, 11.21f, 4.79f, 13f, 7f, 13f)
                horizontalLineTo(7.1f)
                curveTo(7.56f, 14.73f, 8.94f, 16.12f, 10.74f, 16.71f)
                lineTo(10f, 19f)
                horizontalLineTo(7f)
                verticalLineTo(21f)
                horizontalLineTo(17f)
                verticalLineTo(19f)
                horizontalLineTo(14f)
                lineTo(13.26f, 16.71f)
                curveTo(15.06f, 16.12f, 16.44f, 14.73f, 16.9f, 13f)
                horizontalLineTo(17f)
                curveTo(19.21f, 13f, 21f, 11.21f, 21f, 9f)
                verticalLineTo(7f)
                curveTo(21f, 5.9f, 20.1f, 5f, 19f, 5f)
                close()
                moveTo(5f, 9f)
                verticalLineTo(7f)
                horizontalLineTo(7f)
                verticalLineTo(11f)
                curveTo(5.9f, 11f, 5f, 10.1f, 5f, 9f)
                close()
                moveTo(19f, 9f)
                curveTo(19f, 10.1f, 18.1f, 11f, 17f, 11f)
                verticalLineTo(7f)
                horizontalLineTo(19f)
                verticalLineTo(9f)
                close()
            }
        }.build()
    }

    val Book: ImageVector by lazy {
        ImageVector.Builder(
            name = "Book",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(18f, 2f)
                horizontalLineTo(6f)
                curveTo(4.9f, 2f, 4f, 2.9f, 4f, 4f)
                verticalLineTo(20f)
                curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
                horizontalLineTo(18f)
                curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
                verticalLineTo(4f)
                curveTo(20f, 2.9f, 19.1f, 2f, 18f, 2f)
                close()
                moveTo(6f, 4f)
                horizontalLineTo(11f)
                verticalLineTo(12f)
                lineTo(8.5f, 10.5f)
                lineTo(6f, 12f)
                verticalLineTo(4f)
                close()
            }
        }.build()
    }

    val Settings: ImageVector by lazy {
        ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(19.14f, 12.94f)
                curveTo(19.17f, 12.63f, 19.2f, 12.32f, 19.2f, 12f)
                curveTo(19.2f, 11.68f, 19.17f, 11.37f, 19.14f, 11.06f)
                lineTo(21.41f, 9.29f)
                curveTo(21.61f, 9.13f, 21.67f, 8.84f, 21.53f, 8.6f)
                lineTo(19.38f, 4.88f)
                curveTo(19.25f, 4.65f, 18.97f, 4.56f, 18.73f, 4.66f)
                lineTo(16.06f, 5.73f)
                curveTo(15.5f, 5.3f, 14.89f, 4.96f, 14.23f, 4.7f)
                lineTo(13.83f, 1.86f)
                curveTo(13.79f, 1.6f, 13.57f, 1.41f, 13.31f, 1.41f)
                horizontalLineTo(8.98f)
                curveTo(8.72f, 1.41f, 8.5f, 1.6f, 8.46f, 1.86f)
                lineTo(8.06f, 4.7f)
                curveTo(7.4f, 4.96f, 6.79f, 5.3f, 6.23f, 5.73f)
                lineTo(3.56f, 4.66f)
                curveTo(3.32f, 4.56f, 3.03f, 4.65f, 2.91f, 4.88f)
                lineTo(0.76f, 8.6f)
                curveTo(0.62f, 8.84f, 0.68f, 9.13f, 0.88f, 9.29f)
                lineTo(3.15f, 11.06f)
                curveTo(3.12f, 11.37f, 3.09f, 11.68f, 3.09f, 12f)
                curveTo(3.09f, 12.32f, 3.12f, 12.63f, 3.15f, 12.94f)
                lineTo(0.88f, 14.71f)
                curveTo(0.68f, 14.87f, 0.62f, 15.16f, 0.76f, 15.4f)
                lineTo(2.91f, 19.12f)
                curveTo(3.04f, 19.35f, 3.32f, 19.44f, 3.56f, 19.34f)
                lineTo(6.23f, 18.27f)
                curveTo(6.79f, 18.7f, 7.4f, 19.04f, 8.06f, 19.3f)
                lineTo(8.46f, 22.14f)
                curveTo(8.5f, 22.4f, 8.72f, 22.59f, 8.98f, 22.59f)
                horizontalLineTo(13.31f)
                curveTo(13.57f, 22.59f, 13.79f, 22.4f, 13.83f, 22.14f)
                lineTo(14.23f, 19.3f)
                curveTo(14.89f, 19.04f, 15.5f, 18.7f, 16.06f, 18.27f)
                lineTo(18.73f, 19.34f)
                curveTo(18.97f, 19.44f, 19.25f, 19.35f, 19.38f, 19.12f)
                lineTo(21.53f, 15.4f)
                curveTo(21.67f, 15.16f, 21.61f, 14.87f, 21.41f, 14.71f)
                lineTo(19.14f, 12.94f)
                close()
                moveTo(11.14f, 15.5f)
                curveTo(9.21f, 15.5f, 7.64f, 13.93f, 7.64f, 12f)
                curveTo(7.64f, 10.07f, 9.21f, 8.5f, 11.14f, 8.5f)
                curveTo(13.07f, 8.5f, 14.64f, 10.07f, 14.64f, 12f)
                curveTo(14.64f, 13.93f, 13.07f, 15.5f, 11.14f, 15.5f)
                close()
            }
        }.build()
    }

    val Restart: ImageVector by lazy {
        ImageVector.Builder(
            name = "Restart",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(17.65f, 6.35f)
                curveTo(16.2f, 4.9f, 14.21f, 4f, 12f, 4f)
                curveTo(7.58f, 4f, 4.01f, 7.58f, 4.01f, 12f)
                curveTo(4.01f, 16.42f, 7.58f, 20f, 12f, 20f)
                curveTo(15.73f, 20f, 18.84f, 17.45f, 19.73f, 14f)
                horizontalLineTo(17.65f)
                curveTo(16.83f, 16.33f, 14.61f, 18f, 12f, 18f)
                curveTo(8.69f, 18f, 6f, 15.31f, 6f, 12f)
                curveTo(6f, 8.69f, 8.69f, 6f, 12f, 6f)
                curveTo(13.66f, 6f, 15.14f, 6.69f, 16.22f, 7.78f)
                lineTo(13f, 11f)
                horizontalLineTo(20f)
                verticalLineTo(4f)
                lineTo(17.65f, 6.35f)
                close()
            }
        }.build()
    }

    val FitScreen: ImageVector by lazy {
        ImageVector.Builder(
            name = "FitScreen",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(15f, 4f)
                horizontalLineTo(19f)
                verticalLineTo(8f)
                horizontalLineTo(21f)
                verticalLineTo(2f)
                horizontalLineTo(15f)
                verticalLineTo(4f)
                close()
                moveTo(9f, 4f)
                verticalLineTo(2f)
                horizontalLineTo(3f)
                verticalLineTo(8f)
                horizontalLineTo(5f)
                verticalLineTo(4f)
                horizontalLineTo(9f)
                close()
                moveTo(5f, 16f)
                horizontalLineTo(3f)
                verticalLineTo(22f)
                horizontalLineTo(9f)
                verticalLineTo(20f)
                horizontalLineTo(5f)
                verticalLineTo(16f)
                close()
                moveTo(19f, 20f)
                horizontalLineTo(15f)
                verticalLineTo(22f)
                horizontalLineTo(21f)
                verticalLineTo(16f)
                horizontalLineTo(19f)
                verticalLineTo(20f)
                close()
                moveTo(12f, 8f)
                curveTo(9.79f, 8f, 8f, 9.79f, 8f, 12f)
                curveTo(8f, 14.21f, 9.79f, 16f, 12f, 16f)
                curveTo(14.21f, 16f, 16f, 14.21f, 16f, 12f)
                curveTo(16f, 9.79f, 14.21f, 8f, 12f, 8f)
                close()
            }
        }.build()
    }

    val ArrowBack: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(20f, 11f)
                horizontalLineTo(7.83f)
                lineTo(13.42f, 5.41f)
                lineTo(12f, 4f)
                lineTo(4f, 12f)
                lineTo(12f, 20f)
                lineTo(13.41f, 18.59f)
                lineTo(7.83f, 13f)
                horizontalLineTo(20f)
                verticalLineTo(11f)
                close()
            }
        }.build()
    }

    val Shield: ImageVector by lazy {
        ImageVector.Builder(
            name = "Shield",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 1f)
                lineTo(3f, 5f)
                verticalLineTo(11f)
                curveTo(3f, 16.55f, 6.84f, 21.74f, 12f, 23f)
                curveTo(17.16f, 21.74f, 21f, 16.55f, 21f, 11f)
                verticalLineTo(5f)
                lineTo(12f, 1f)
                close()
            }
        }.build()
    }

    val Lightning: ImageVector by lazy {
        ImageVector.Builder(
            name = "Lightning",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(7f, 2f)
                verticalLineTo(13f)
                horizontalLineTo(10f)
                verticalLineTo(22f)
                lineTo(17f, 10f)
                horizontalLineTo(13f)
                lineTo(17f, 2f)
                horizontalLineTo(7f)
                close()
            }
        }.build()
    }

    val Target: ImageVector by lazy {
        ImageVector.Builder(
            name = "Target",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 2f)
                curveTo(6.49f, 2f, 2f, 6.49f, 2f, 12f)
                curveTo(2f, 17.51f, 6.49f, 22f, 12f, 22f)
                curveTo(17.51f, 22f, 22f, 17.51f, 22f, 12f)
                curveTo(22f, 6.49f, 17.51f, 2f, 12f, 2f)
                close()
                moveTo(12f, 20f)
                curveTo(7.59f, 20f, 4f, 16.41f, 4f, 12f)
                curveTo(4f, 7.59f, 7.59f, 4f, 12f, 4f)
                curveTo(16.41f, 4f, 20f, 7.59f, 20f, 12f)
                curveTo(20f, 16.41f, 16.41f, 20f, 12f, 20f)
                close()
                moveTo(12f, 6f)
                curveTo(8.69f, 6f, 6f, 8.69f, 6f, 12f)
                curveTo(6f, 15.31f, 8.69f, 18f, 12f, 18f)
                curveTo(15.31f, 18f, 18f, 15.31f, 18f, 12f)
                curveTo(18f, 8.69f, 15.31f, 6f, 12f, 6f)
                close()
                moveTo(12f, 14f)
                curveTo(10.9f, 14f, 10f, 13.1f, 10f, 12f)
                curveTo(10f, 10.9f, 10.9f, 10f, 12f, 10f)
                curveTo(13.1f, 10f, 14f, 10.9f, 14f, 12f)
                curveTo(14f, 13.1f, 13.1f, 14f, 12f, 14f)
                close()
            }
        }.build()
    }

    val Sun: ImageVector by lazy {
        ImageVector.Builder(
            name = "Sun",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12f, 6.5f)
                curveTo(8.96f, 6.5f, 6.5f, 8.96f, 6.5f, 12f)
                curveTo(6.5f, 15.04f, 8.96f, 17.5f, 12f, 17.5f)
                curveTo(15.04f, 17.5f, 17.5f, 15.04f, 17.5f, 12f)
                curveTo(17.5f, 8.96f, 15.04f, 6.5f, 12f, 6.5f)
                close()
                moveTo(11f, 1f)
                horizontalLineTo(13f)
                verticalLineTo(5f)
                horizontalLineTo(11f)
                close()
                moveTo(11f, 19f)
                horizontalLineTo(13f)
                verticalLineTo(23f)
                horizontalLineTo(11f)
                close()
                moveTo(1f, 11f)
                verticalLineTo(13f)
                horizontalLineTo(5f)
                verticalLineTo(11f)
                close()
                moveTo(19f, 11f)
                verticalLineTo(13f)
                horizontalLineTo(23f)
                verticalLineTo(11f)
                close()
                moveTo(4.93f, 3.51f)
                lineTo(6.34f, 2.1f)
                lineTo(9.17f, 4.93f)
                lineTo(7.76f, 6.34f)
                close()
                moveTo(14.83f, 17.66f)
                lineTo(16.24f, 16.24f)
                lineTo(20.49f, 20.49f)
                lineTo(19.07f, 21.9f)
                close()
                moveTo(17.66f, 9.17f)
                lineTo(16.24f, 7.76f)
                lineTo(20.49f, 3.51f)
                lineTo(21.9f, 4.93f)
                close()
                moveTo(3.51f, 20.49f)
                lineTo(7.76f, 16.24f)
                lineTo(9.17f, 17.66f)
                lineTo(4.93f, 21.9f)
                close()
            }
        }.build()
    }

    val Moon: ImageVector by lazy {
        ImageVector.Builder(
            name = "Moon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12.3f, 2f)
                curveTo(6.98f, 2.09f, 2.73f, 6.57f, 3.03f, 11.9f)
                curveTo(3.29f, 16.48f, 6.94f, 20.24f, 11.52f, 20.93f)
                curveTo(16.29f, 21.65f, 20.57f, 18.52f, 21.78f, 14.16f)
                curveTo(22.06f, 13.15f, 21.23f, 12.24f, 20.19f, 12.34f)
                curveTo(15.4f, 12.8f, 11.23f, 8.65f, 11.66f, 3.84f)
                curveTo(11.75f, 2.79f, 10.84f, 1.97f, 9.83f, 2.25f)
                curveTo(10.65f, 2.08f, 11.48f, 2f, 12.3f, 2f)
                close()
            }
        }.build()
    }

    val AutoMode: ImageVector by lazy {
        ImageVector.Builder(
            name = "AutoMode",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(20f, 3f)
                horizontalLineTo(4f)
                curveTo(2.89f, 3f, 2f, 3.89f, 2f, 5f)
                verticalLineTo(15f)
                curveTo(2f, 16.1f, 2.89f, 17f, 4f, 17f)
                horizontalLineTo(10f)
                verticalLineTo(19f)
                horizontalLineTo(8f)
                verticalLineTo(21f)
                horizontalLineTo(16f)
                verticalLineTo(19f)
                horizontalLineTo(14f)
                verticalLineTo(17f)
                horizontalLineTo(20f)
                curveTo(21.1f, 17f, 22f, 16.1f, 22f, 15f)
                verticalLineTo(5f)
                curveTo(22f, 3.89f, 21.1f, 3f, 20f, 3f)
                close()
                moveTo(20f, 15f)
                horizontalLineTo(4f)
                verticalLineTo(5f)
                horizontalLineTo(20f)
                verticalLineTo(15f)
                close()
            }
        }.build()
    }

    val Add: ImageVector by lazy {
        ImageVector.Builder(
            name = "Add",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(19f, 13f)
                horizontalLineTo(13f)
                verticalLineTo(19f)
                horizontalLineTo(11f)
                verticalLineTo(13f)
                horizontalLineTo(5f)
                verticalLineTo(11f)
                horizontalLineTo(11f)
                verticalLineTo(5f)
                horizontalLineTo(13f)
                verticalLineTo(11f)
                horizontalLineTo(19f)
                verticalLineTo(13f)
                close()
            }
        }.build()
    }

    val Remove: ImageVector by lazy {
        ImageVector.Builder(
            name = "Remove",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(19f, 13f)
                horizontalLineTo(5f)
                verticalLineTo(11f)
                horizontalLineTo(19f)
                verticalLineTo(13f)
                close()
            }
        }.build()
    }
}

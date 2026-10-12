package com.exp.saolei

import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skia.Image

fun main() = application {
    val windowState = rememberWindowState(width = 960.dp, height = 840.dp)
    val appIcon = remember {
        try {
            val bytes = object {}.javaClass.getResourceAsStream("/icon.png")?.use { it.readBytes() }
            bytes?.let { Image.makeFromEncoded(it).toComposeImageBitmap() }?.let { BitmapPainter(it) }
        } catch (_: Throwable) {
            null
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "扫雷",
        icon = appIcon,
        state = windowState
    ) {
        App()
    }
}
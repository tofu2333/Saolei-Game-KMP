package com.exp.saolei.audio

actual class AudioPlayer actual constructor() {
    actual fun playSound(type: SoundType, volume: Float) {}
    actual fun startBgm(volume: Float) {}
    actual fun stopBgm() {}
    actual fun updateBgmVolume(volume: Float) {}
    actual fun release() {}
}

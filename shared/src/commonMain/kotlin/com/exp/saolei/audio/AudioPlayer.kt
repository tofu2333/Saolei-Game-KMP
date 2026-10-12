package com.exp.saolei.audio

expect class AudioPlayer() {
    fun playSound(type: SoundType, volume: Float)
    fun startBgm(volume: Float)
    fun stopBgm()
    fun updateBgmVolume(volume: Float)
    fun release()
}

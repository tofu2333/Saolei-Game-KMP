package com.exp.saolei.audio

import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

actual class AudioPlayer actual constructor() {
    private val format = AudioFormat(
        WaveformSynthesizer.SAMPLE_RATE.toFloat(),
        16,
        1,
        true,
        false
    )

    private val soundCache = mutableMapOf<SoundType, ByteArray>()
    private var bgmClip: Clip? = null
    private var currentBgmVolume: Float = 0.5f

    init {
        // Pre-generate waveforms in background or lazily
        SoundType.entries.forEach { type ->
            soundCache[type] = WaveformSynthesizer.generateSound(type)
        }
    }

    private fun setClipVolume(clip: Clip, volume: Float) {
        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                val gainControl = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
                val min = gainControl.minimum
                val max = gainControl.maximum
                val v = volume.coerceIn(0.0001f, 1f)
                val dB = (Math.log10(v.toDouble()) * 20.0).toFloat().coerceIn(min, max)
                gainControl.value = dB
            }
        } catch (e: Exception) {
            // Ignore volume control errors
        }
    }

    actual fun playSound(type: SoundType, volume: Float) {
        if (volume <= 0f) return
        try {
            val bytes = soundCache[type] ?: WaveformSynthesizer.generateSound(type).also { soundCache[type] = it }
            val bais = ByteArrayInputStream(bytes)
            val ais = AudioInputStream(bais, format, (bytes.size / 2).toLong())
            val clip = AudioSystem.getClip()
            clip.open(ais)
            setClipVolume(clip, volume)
            clip.start()
        } catch (e: Exception) {
            // Audio line unavailable or headful issue
        }
    }

    actual fun startBgm(volume: Float) {
        currentBgmVolume = volume
        if (volume <= 0f) return
        stopBgm()
        try {
            val bgmBytes = WaveformSynthesizer.generateBgmLoop()
            val bais = ByteArrayInputStream(bgmBytes)
            val ais = AudioInputStream(bais, format, (bgmBytes.size / 2).toLong())
            val clip = AudioSystem.getClip()
            clip.open(ais)
            setClipVolume(clip, volume)
            clip.loop(Clip.LOOP_CONTINUOUSLY)
            clip.start()
            bgmClip = clip
        } catch (e: Exception) {
            // Audio line unavailable
        }
    }

    actual fun stopBgm() {
        try {
            bgmClip?.stop()
            bgmClip?.close()
            bgmClip = null
        } catch (e: Exception) {
            // Ignore
        }
    }

    actual fun updateBgmVolume(volume: Float) {
        currentBgmVolume = volume
        val clip = bgmClip
        if (clip != null && clip.isRunning) {
            if (volume <= 0f) {
                stopBgm()
            } else {
                setClipVolume(clip, volume)
            }
        } else if (volume > 0f && clip == null) {
            startBgm(volume)
        }
    }

    actual fun release() {
        stopBgm()
    }
}

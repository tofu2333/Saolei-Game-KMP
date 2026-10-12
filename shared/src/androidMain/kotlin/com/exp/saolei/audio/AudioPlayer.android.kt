package com.exp.saolei.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack

actual class AudioPlayer actual constructor() {
    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val audioFormat = AudioFormat.Builder()
        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
        .setSampleRate(WaveformSynthesizer.SAMPLE_RATE)
        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
        .build()

    private val soundTracks = mutableMapOf<SoundType, ByteArray>()
    private var bgmTrack: AudioTrack? = null
    private var currentBgmVolume: Float = 0.5f

    init {
        SoundType.entries.forEach { type ->
            soundTracks[type] = WaveformSynthesizer.generateSound(type)
        }
    }

    actual fun playSound(type: SoundType, volume: Float) {
        if (volume <= 0f) return
        try {
            val bytes = soundTracks[type] ?: return
            val track = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(bytes.size)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.setVolume(volume.coerceIn(0f, 1f))
            track.write(bytes, 0, bytes.size)
            track.play()
            // Clean up when done
            track.setNotificationMarkerPosition(bytes.size / 2)
            track.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
                override fun onMarkerReached(t: AudioTrack?) {
                    try {
                        t?.stop()
                        t?.release()
                    } catch (e: Exception) {}
                }
                override fun onPeriodicNotification(t: AudioTrack?) {}
            })
        } catch (e: Exception) {
            // AudioTrack allocation failure fallback
        }
    }

    actual fun startBgm(volume: Float) {
        currentBgmVolume = volume
        if (volume <= 0f) return
        stopBgm()
        try {
            val bytes = WaveformSynthesizer.generateBgmLoop()
            val totalSamples = bytes.size / 2
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(bytes.size)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.setVolume(volume.coerceIn(0f, 1f))
            track.write(bytes, 0, bytes.size)
            track.setLoopPoints(0, totalSamples, -1)
            track.play()
            bgmTrack = track
        } catch (e: Exception) {
            // Audio error
        }
    }

    actual fun stopBgm() {
        try {
            bgmTrack?.stop()
            bgmTrack?.release()
            bgmTrack = null
        } catch (e: Exception) {
            // Ignore
        }
    }

    actual fun updateBgmVolume(volume: Float) {
        currentBgmVolume = volume
        try {
            bgmTrack?.setVolume(volume.coerceIn(0f, 1f))
            if (volume <= 0f && bgmTrack != null) {
                stopBgm()
            } else if (volume > 0f && bgmTrack == null) {
                startBgm(volume)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    actual fun release() {
        stopBgm()
    }
}

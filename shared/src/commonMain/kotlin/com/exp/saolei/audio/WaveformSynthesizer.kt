package com.exp.saolei.audio

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

enum class SoundType {
    CLICK,
    DIG,
    FLAG,
    UNFLAG,
    CHORD,
    EXPLODE,
    WIN
}

object WaveformSynthesizer {
    const val SAMPLE_RATE = 22050

    private fun sampleToBytes(samples: ShortArray): ByteArray {
        val bytes = ByteArray(samples.size * 2)
        for (i in samples.indices) {
            val s = samples[i].toInt()
            bytes[i * 2] = (s and 0xFF).toByte()
            bytes[i * 2 + 1] = ((s shr 8) and 0xFF).toByte()
        }
        return bytes
    }

    fun generateSound(type: SoundType): ByteArray {
        return when (type) {
            SoundType.CLICK -> generateClick()
            SoundType.DIG -> generateDig()
            SoundType.FLAG -> generateFlag()
            SoundType.UNFLAG -> generateUnflag()
            SoundType.CHORD -> generateChord()
            SoundType.EXPLODE -> generateExplode()
            SoundType.WIN -> generateWin()
        }
    }

    private fun generateClick(): ByteArray {
        val durationSec = 0.02
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-t * 250.0)
            val wave = sin(2.0 * PI * 1200.0 * t)
            samples[i] = (wave * envelope * 22000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateDig(): ByteArray {
        val durationSec = 0.05
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val freq = 420.0 - (t / durationSec) * 260.0
            val envelope = (1.0 - t / durationSec)
            val wave = sin(2.0 * PI * freq * t)
            samples[i] = (wave * envelope * 24000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateFlag(): ByteArray {
        val durationSec = 0.08
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-t * 40.0)
            val wave = 0.6 * sin(2.0 * PI * 988.0 * t) + 0.4 * sin(2.0 * PI * 1318.0 * t)
            samples[i] = (wave * envelope * 26000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateUnflag(): ByteArray {
        val durationSec = 0.04
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-t * 80.0)
            val wave = sin(2.0 * PI * 650.0 * t)
            samples[i] = (wave * envelope * 18000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateChord(): ByteArray {
        val durationSec = 0.12
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-t * 30.0)
            val wave = (sin(2.0 * PI * 523.25 * t) + sin(2.0 * PI * 659.25 * t) + sin(2.0 * PI * 783.99 * t)) / 3.0
            samples[i] = (wave * envelope * 26000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateExplode(): ByteArray {
        val durationSec = 0.45
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        val rng = Random(42)
        var lastNoise = 0.0
        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-t * 8.0)
            val noise = (rng.nextDouble() * 2.0 - 1.0)
            // Low-pass filtered noise
            lastNoise = lastNoise * 0.85 + noise * 0.15
            val sub = sin(2.0 * PI * 60.0 * t)
            val combined = (lastNoise * 0.7 + sub * 0.3)
            samples[i] = (combined * envelope * 30000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    private fun generateWin(): ByteArray {
        val durationSec = 0.75
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)
        val notes = doubleArrayOf(523.25, 659.25, 783.99, 1046.50)
        val noteDur = durationSec / notes.size

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val noteIndex = (t / noteDur).toInt().coerceIn(0, notes.size - 1)
            val noteT = t - noteIndex * noteDur
            val freq = notes[noteIndex]
            val env = exp(-noteT * 6.0)
            val wave = sin(2.0 * PI * freq * t) + 0.3 * sin(2.0 * PI * freq * 2.0 * t)
            samples[i] = (wave * env * 24000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }

    fun generateBgmLoop(): ByteArray {
        // Peaceful ambient chord progression loop (4 chords, 6.0 seconds)
        val durationSec = 6.0
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val samples = ShortArray(totalSamples)

        val chords = listOf(
            doubleArrayOf(261.63, 329.63, 392.00, 493.88), // Cmaj7
            doubleArrayOf(220.00, 261.63, 329.63, 392.00), // Am7
            doubleArrayOf(174.61, 220.00, 261.63, 329.63), // Fmaj7
            doubleArrayOf(196.00, 246.94, 293.66, 349.23)  // G7
        )
        val chordDur = durationSec / chords.size

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val chordIndex = (t / chordDur).toInt().coerceIn(0, chords.size - 1)
            val currentChord = chords[chordIndex]
            val chordT = t - chordIndex * chordDur

            // Bell-shaped envelope for each chord transition
            val fadeTime = 0.3
            val fadeIn = (chordT / fadeTime).coerceIn(0.0, 1.0)
            val fadeOut = ((chordDur - chordT) / fadeTime).coerceIn(0.0, 1.0)
            val env = fadeIn * fadeOut

            var wave = 0.0
            for (freq in currentChord) {
                wave += sin(2.0 * PI * freq * t)
            }
            wave /= currentChord.size

            samples[i] = (wave * env * 12000.0).toInt().coerceIn(-32767, 32767).toShort()
        }
        return sampleToBytes(samples)
    }
}

package com.example.pylearn.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.example.pylearn.R

class AndroidSoundPlayer(
    context: Context
) : SoundPlayer {

    private val soundPool: SoundPool

    private val soundIds: Map<SoundEffect, Int>

    private val loadedSoundIds =
        mutableSetOf<Int>()

    private val pendingSoundIds =
        mutableSetOf<Int>()

    init {
        val audioAttributes =
            AudioAttributes.Builder()
                .setUsage(
                    AudioAttributes.USAGE_GAME
                )
                .setContentType(
                    AudioAttributes.CONTENT_TYPE_SONIFICATION
                )
                .build()

        soundPool =
            SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build()

        soundPool.setOnLoadCompleteListener {
                _,
                sampleId,
                status ->

            if (status == 0) {
                loadedSoundIds.add(sampleId)

                Log.d(
                    TAG,
                    "Sound loaded successfully: $sampleId"
                )

                if (sampleId in pendingSoundIds) {
                    pendingSoundIds.remove(sampleId)
                    playLoadedSound(sampleId)
                }
            } else {
                Log.e(
                    TAG,
                    "Sound failed to load: $sampleId, status: $status"
                )
            }
        }

        soundIds = mapOf(
            SoundEffect.CORRECT_ANSWER to
                    soundPool.load(
                        context,
                        R.raw.correct_answer,
                        1
                    ),

            SoundEffect.INCORRECT_ANSWER to
                    soundPool.load(
                        context,
                        R.raw.incorrect_answer,
                        1
                    ),

            SoundEffect.ACTIVITY_COMPLETE to
                    soundPool.load(
                        context,
                        R.raw.activity_complete,
                        1
                    ),

            SoundEffect.FLASHCARD_FLIP to
                    soundPool.load(
                        context,
                        R.raw.flashcard_flip,
                        1
                    )
        )
    }

    override fun play(
        effect: SoundEffect,
        enabled: Boolean
    ) {
        Log.d(
            TAG,
            "Sound requested: $effect, enabled: $enabled"
        )

        if (!enabled) {
            return
        }

        val soundId = soundIds[effect]

        if (soundId == null) {
            Log.e(
                TAG,
                "No sound resource found for $effect"
            )
            return
        }

        if (soundId in loadedSoundIds) {
            playLoadedSound(soundId)
        } else {
            Log.d(
                TAG,
                "Sound not loaded yet. Queuing: $soundId"
            )

            pendingSoundIds.add(soundId)
        }
    }

    private fun playLoadedSound(
        soundId: Int
    ) {
        val streamId =
            soundPool.play(
                soundId,
                VOLUME,
                VOLUME,
                1,
                0,
                1f
            )

        Log.d(
            TAG,
            "SoundPool play result: $streamId"
        )

        if (streamId == 0) {
            Log.e(
                TAG,
                "SoundPool was unable to play sound: $soundId"
            )
        }
    }

    override fun release() {
        pendingSoundIds.clear()
        loadedSoundIds.clear()
        soundPool.release()
    }

    private companion object {
        const val TAG = "PyLearnSound"
        const val VOLUME = 1f
    }
}
package com.example.pylearn.audio

interface SoundPlayer {

    fun play(
        effect: SoundEffect,
        enabled: Boolean
    )

    fun release()
}
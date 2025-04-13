package com.example.compositiongame.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Serializable
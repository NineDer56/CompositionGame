package com.example.compositiongame.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)
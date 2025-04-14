package com.example.compositiongame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers : Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable{
    val percentage : Int
        get() = ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
}
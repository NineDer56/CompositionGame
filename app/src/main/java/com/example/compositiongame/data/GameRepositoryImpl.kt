package com.example.compositiongame.data

import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.domain.entity.Level.*
import com.example.compositiongame.domain.entity.Question
import com.example.compositiongame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)

        val from = max(MIN_ANSWER_VALUE, rightAnswer - countOfOptions)
        val to = min(maxSumValue, rightAnswer + countOfOptions)

        while (options.size != countOfOptions) {
            options.add(Random.nextInt(from, to))
        }

        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            EASY -> GameSettings(
                10,
                10,
                60,
                60
            )
            MEDIUM -> GameSettings(
                20,
                20,
                80,
                40
            )
            HARD -> GameSettings(
                100,
                30,
                90,
                40
            )
        }
    }
}
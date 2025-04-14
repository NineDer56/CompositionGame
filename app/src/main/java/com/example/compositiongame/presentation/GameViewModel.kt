package com.example.compositiongame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compositiongame.R
import com.example.compositiongame.data.GameRepositoryImpl
import com.example.compositiongame.domain.entity.GameResult
import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.domain.entity.Question
import com.example.compositiongame.domain.usecase.GenerateQuestionUseCase
import com.example.compositiongame.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    val context = application

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private lateinit var timer: CountDownTimer

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime : LiveData<String>
        get() = _formatedTime

    private val _question = MutableLiveData<Question>()
    val question : LiveData<Question>
        get() = _question

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers : LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount : LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent : LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult : LiveData<GameResult>
        get() = _gameResult


    fun startGame(level : Level){
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(level : Level){
        this.level = level
        this.gameSettings = getGameSettingsUseCase.invoke(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer(){
        timer = object : CountDownTimer(gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND, MILLIS_IN_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _formatedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer.start()
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase.invoke(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long) : String{
        val sec = millisUntilFinished / 1000 % 60
        val min = millisUntilFinished / 60000 % 60
        return String.format("%02d:%02d", min, sec)
    }

    fun chooseAnswer(number: Int){
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number : Int){
        val rightAnswer = question.value?.rightAnswer
        if(number == rightAnswer){
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.right_answers_amount),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )

        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers() : Int{
        if(countOfQuestions == 0) return 0
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun finishGame(){
        _gameResult.value = GameResult(
             enoughCount.value == true && enoughPercent.value == true,
             countOfRightAnswers,
             countOfQuestions,
             gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }


    companion object{
        private const val MILLIS_IN_SECOND = 1000L
    }

}
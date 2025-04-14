package com.example.compositiongame.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.FragmentManager
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentGameFinishedBinding
import com.example.compositiongame.databinding.FragmentWelcomeBinding
import com.example.compositiongame.domain.entity.GameResult


class GameFinishedFragment : Fragment() {

    private var _binding : FragmentGameFinishedBinding? = null
    private val binding : FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding is null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        })

        with(binding){

            buttonTryAgain.setOnClickListener{
                retryGame()
            }

            when(gameResult.winner){
                true -> imageViewEmoji.setImageResource(R.drawable.ic_smile)
                false -> imageViewEmoji.setImageResource(R.drawable.ic_sad)
            }

            TextViewRequiredAnswers.text = String.format(getString(R.string.required_amount_of_right_answers), gameResult.gameSettings.minCountOfRightAnswers.toString())

            TextViewAnswers.text = String.format(getString(R.string.your_score), gameResult.countOfRightAnswers.toString())

            TextViewRequiredPercentage.text = String.format(getString(R.string.required_percent_of_right_answers), gameResult.gameSettings.minPercentOfRightAnswers.toString())

            TextViewPercentage.text = String.format(getString(R.string.right_answers_percentage),gameResult.percentage.toString())

        }
    }

    private fun retryGame(){
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, 1)
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
       requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT) ?. let {
           gameResult = it
       }
    }

    companion object{

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult) : GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}
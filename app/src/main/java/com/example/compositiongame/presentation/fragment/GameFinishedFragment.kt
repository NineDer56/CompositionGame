package com.example.compositiongame.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentGameFinishedBinding
import com.example.compositiongame.databinding.FragmentWelcomeBinding
import com.example.compositiongame.domain.entity.GameResult


class GameFinishedFragment : Fragment() {

    private var _binding : FragmentGameFinishedBinding? = null
    private val binding : FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding is null")

    private val args by navArgs<GameFinishedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            buttonTryAgain.setOnClickListener{
                retryGame()
            }

            when(args.gameResult.winner){
                true -> imageViewEmoji.setImageResource(R.drawable.ic_smile)
                false -> imageViewEmoji.setImageResource(R.drawable.ic_sad)
            }

            TextViewRequiredAnswers.text = String.format(
                getString(R.string.required_amount_of_right_answers),
                args.gameResult.gameSettings.minCountOfRightAnswers.toString()
            )

            TextViewAnswers.text = String.format(
                getString(R.string.your_score),
                args.gameResult.countOfRightAnswers.toString()
            )

            TextViewRequiredPercentage.text = String.format(
                getString(R.string.required_percent_of_right_answers),
                args.gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )

            TextViewPercentage.text = String.format(
                getString(R.string.right_answers_percentage),
                args.gameResult.percentage.toString()
            )
        }
    }

    private fun retryGame(){
        findNavController().popBackStack()
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}
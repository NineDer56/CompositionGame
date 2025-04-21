package com.example.compositiongame.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentGameBinding
import com.example.compositiongame.domain.entity.GameResult
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.presentation.GameViewModel
import com.example.compositiongame.presentation.GameViewModelFactory

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory : GameViewModelFactory by lazy {
        GameViewModelFactory(
            requireActivity().application,
            args.level
        )
    }

    private val viewModel: GameViewModel by lazy{
        ViewModelProvider(
            this,
            viewModelFactory
        )[(GameViewModel::class.java)]
    }

    private val textViewOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.textViewOption1)
            add(binding.textViewOption2)
            add(binding.textViewOption3)
            add(binding.textViewOption4)
            add(binding.textViewOption5)
            add(binding.textViewOption6)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnOptionsClickListeners()
    }


    private fun setOnOptionsClickListeners(){
        for(option in textViewOptions){
            option.setOnClickListener {
                viewModel.chooseAnswer(option.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel(){
        with(viewModel){
            gameResult.observe(viewLifecycleOwner){
                launchGameFinishedFragment(it)
            }

            question.observe(viewLifecycleOwner){
                with(binding){
                    textViewSum.text = it.sum.toString()
                    textViewVisibleNumber.text = it.visibleNumber.toString()

                    for((index, option) in it.options.withIndex()){
                        textViewOptions[index].text = option.toString()
                    }
                }
            }

            formatedTime.observe(viewLifecycleOwner){
                binding.textViewTimer.text = it
            }

            minPercent.observe(viewLifecycleOwner){
                binding.progressBar.secondaryProgress = it
            }

            enoughCount.observe(viewLifecycleOwner){
                binding.textViewRightAnswers.setTextColor(getColor(it))
            }

            enoughPercent.observe(viewLifecycleOwner){
                binding.progressBar.progressTintList = ColorStateList.valueOf(getColor(it))
            }

            percentOfRightAnswers.observe(viewLifecycleOwner){
                binding.progressBar.setProgress(it, true)
            }

            progressAnswers.observe(viewLifecycleOwner){
                binding.textViewRightAnswers.text = it
            }
        }
    }

    private fun getColor(isGreen : Boolean) : Int{
        return if(isGreen) {
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_light)
        } else {
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        }
    }


    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
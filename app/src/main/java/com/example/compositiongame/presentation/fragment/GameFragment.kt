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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel  // теперь изменения будут автоматически прилетать в binding
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }


    private fun observeViewModel(){
        with(viewModel){
            gameResult.observe(viewLifecycleOwner){
                launchGameFinishedFragment(it)
            }
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
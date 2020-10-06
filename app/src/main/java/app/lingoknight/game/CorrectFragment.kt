//Michael Carter
// 1910059

package app.lingoknight.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentCorrectAnswerBinding


class CorrectFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCorrectAnswerBinding.inflate(inflater)
        // Giving the binding access to the GameViewModel
        binding.gameViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        // Observes changes to player
        viewModel.player.observe(viewLifecycleOwner, {
            viewBinding(binding)
        })
        // Observes changes to currentQuestion
        viewModel.currentQuestion.observe(viewLifecycleOwner, {
            viewModel.randomAnswers()
            viewBinding(binding)
        })

        // When button is clicked performing a check to see if the game has ended
        binding.nextBtn.setOnClickListener {


            // If true navigate to the score fragment
            if (viewModel.isEnd()) {
                view?.findNavController()?.navigate(R.id.action_correctFragment_to_scoreFragment)
            } else {
                view?.findNavController()?.navigate(R.id.action_correctFragment_to_gameFragment)
                // called a list of commands to progress the game to the next question
                viewModel.progressQuestion()
            }
        }

        return binding.root
    }

    private fun viewBinding(binding: FragmentCorrectAnswerBinding) {
        binding.answer.text = viewModel.currentQuestion.value?.answers?.get(0)

    }


}


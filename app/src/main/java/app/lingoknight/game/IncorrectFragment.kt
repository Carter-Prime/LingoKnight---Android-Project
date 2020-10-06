//Michael Carter
// 1910059

package app.lingoknight.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentIncorrectAnswerBinding


class IncorrectFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentIncorrectAnswerBinding.inflate(inflater)
        // Giving the binding access to the GameViewModel
        binding.gameViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        // Observes changes to player
        viewModel.player.observe(viewLifecycleOwner, Observer {
            viewBinding(binding)
        })
        // Observes changes to currentQuestion
        viewModel.currentQuestion.observe(viewLifecycleOwner, {
            viewModel.randomAnswers()
            viewBinding(binding)
        })

        // On click checks if the game has ended and sends to score screen, else returns to question fragment
        binding.nextBtn.setOnClickListener {

            if (viewModel.isEnd()) {
                view?.findNavController()?.navigate(R.id.action_incorrectFragment_to_scoreFragment)
            } else {
                view?.findNavController()?.navigate(R.id.action_incorrectFragment_to_gameFragment)
                viewModel.progressQuestion()
            }

        }

        return binding.root
    }

    private fun viewBinding(binding: FragmentIncorrectAnswerBinding) {
        binding.answer.text = viewModel.currentQuestion.value?.answers?.get(0)
    }


}

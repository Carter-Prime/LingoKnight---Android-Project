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
import app.lingoknight.databinding.FragmentGameBinding

// UI fragment for the questions aspect of the game

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentGameBinding.inflate(inflater)
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

        binding.nextBtn.setOnClickListener {
            checkAnswer(binding)
        }

        return binding.root
    }

    private fun viewBinding(binding: FragmentGameBinding){
        binding.gameQuestion.text = viewModel.currentQuestion.value?.text
        binding.radioBtnAnswerOne.text = viewModel.answerList[0]
        binding.radioBtnAnswerTwo.text = viewModel.answerList[1]
        binding.radioBtnAnswerThree.text = viewModel.answerList[2]
        binding.radioBtnAnswerFour.text = viewModel.answerList[3]
        binding.wordImageGame.setImageResource(
            when (viewModel.currentQuestion.value?.wordId) {
                "king" -> R.drawable.king
                "knight" -> R.drawable.knight
                "princess" -> R.drawable.princess
                "witch" -> R.drawable.witch
                "fox" -> R.drawable.fox
                "purple" -> R.drawable.purple_monster
                "blue" -> R.drawable.blue_bird
                "dragon" -> R.drawable.dragon
                "monster" -> R.drawable.green_monster
                "green" -> R.drawable.green_troll
                "tree" -> R.drawable.tree
                "yellow" -> R.drawable.yellow_monster
                "horse" -> R.drawable.horse
                "goat" -> R.drawable.goat
                else -> R.drawable.oops_comic
            }
        )
    }

    // Performing checks on the radio buttons to see if the answer matches that of the question
    // If correct a point is added to the player score and the game will progress to the correct or
    // incorrect fragment.
    private fun checkAnswer(binding: FragmentGameBinding) {
        val radioId = binding.answerGroup.checkedRadioButtonId
        // Do nothing if nothing is checked (id == -1)
        if (-1 != radioId) {
            var answerIndex = 0
            when (radioId) {
                R.id.radioBtn_answerOne -> answerIndex = 0
                R.id.radioBtn_answerTwo -> answerIndex = 1
                R.id.radioBtn_answerThree -> answerIndex = 2
                R.id.radioBtn_answerFour -> answerIndex = 3
            }

            if(viewModel.isCorrect(answerIndex)){
                viewModel.score += 1
                viewModel.increaseSeen()
                viewModel.increaseCorrect()
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_correctFragment)
            }else if(!viewModel.isCorrect(answerIndex)){
                viewModel.increaseSeen()
                viewModel.increaseIncorrect()
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_incorrectFragment)
            }
        }
    }
}

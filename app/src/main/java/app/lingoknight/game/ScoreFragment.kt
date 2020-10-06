//Michael Carter
// 1910059

package app.lingoknight.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentScoreBinding.inflate(inflater)
        // Giving the binding access to the GameViewModel
        binding.gameViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        // Observes changes to player
        viewModel.player.observe(viewLifecycleOwner, {
            Log.d("testing", "player observer ${viewModel.player.value?.playerScore}")
            viewBinding(binding)
        })
        // Observes changes to currentQuestion
        viewModel.currentQuestion.observe(viewLifecycleOwner, {
            viewBinding(binding)
        })

        binding.nextBtn.setOnClickListener {
            viewModel.updatePlayerScore()
            view?.findNavController()?.navigate(R.id.action_scoreFragment_to_titleFragment)


        }

        return binding.root
    }

    private fun viewBinding(binding: FragmentScoreBinding) {
        binding.playerScore.text = viewModel.score.toString()
        binding.playerImageScore.setImageResource(
            when (viewModel.player.value?.pictureId) {
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


}

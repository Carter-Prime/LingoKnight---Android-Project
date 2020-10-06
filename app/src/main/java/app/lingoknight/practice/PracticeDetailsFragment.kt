//Michael Carter
// 1910059

package app.lingoknight.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.lingoknight.R
import app.lingoknight.database.Word
import app.lingoknight.databinding.FragmentPracticeDetailBinding


class PracticeDetailsFragment : Fragment() {

    private val viewModel: PracticeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPracticeDetailBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel

        // Observer to the live data word
        viewModel.word.observe(viewLifecycleOwner, { words ->
            words?.apply {
                bind(words, binding)
            }
        })

        return binding.root

    }


    private fun bind(item: Word?, view: FragmentPracticeDetailBinding) {
        view.wordTextDetail.text = item?.text
        view.correctNumber.text = item?.wordCorrect.toString()
        view.incorrectNumber.text = item?.wordIncorrect.toString()
        view.seenNumber.text = item?.wordSeen.toString()
        view.wordImageDetail.setImageResource(
            when (item?.id) {
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
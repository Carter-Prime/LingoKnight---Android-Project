//Michael Carter
// 1910059

package app.lingoknight.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentResetStatsBinding
import app.lingoknight.practice.PracticeViewModel


class ResetStatsFragment : Fragment() {

    private val viewModel: PracticeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentResetStatsBinding.inflate(inflater)
        binding.practiceViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        binding.resetButton.setOnClickListener {
            viewModel.hardReset()
            view?.findNavController()?.navigate(R.id.action_resetStatsFragment_to_titleFragment)
        }

        return binding.root
    }

}
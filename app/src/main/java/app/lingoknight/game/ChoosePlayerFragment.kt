package app.lingoknight.game
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.lingoknight.R
import app.lingoknight.database.Player
import app.lingoknight.database.Word
import app.lingoknight.databinding.FragmentChoosePlayerBinding
import app.lingoknight.practice.PracticeFragment


class ChoosePlayerFragment : Fragment(), PlayerAdapter.Interaction {

    override fun onItemSelected(position: Int, item: Player) {
        viewModel.charPicked(position, item, this)
    }

    private val viewModel: GameViewModel by activityViewModels()

    private var playerListAdapter = PlayerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentChoosePlayerBinding.inflate(inflater)
        // Giving the binding access to the PracticeViewModel
        binding.gameViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        initRecyclerView(binding)

        viewModel.listOfWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords
                Log.d("testing", "List of Words Observe: ${viewModel.listOfWords.value?.size}")

            }
        })

        viewModel.listOfQuestion.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfQuestions
                Log.d("testing", "List of Questions Observer: ${viewModel.listOfQuestion.value?.size}")
            }
        })

        viewModel.listOfPlayers.observe(viewLifecycleOwner, { players ->
            players?.apply {
                // what happens to the view on update to listOfPlayers
                playerListAdapter.submitList(viewModel.listOfPlayers)
            }
        })
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentChoosePlayerBinding){
        Log.d("testing", "1. inside initialisation of recyclerView ")
        binding.playerListRecyclerView.layoutManager = LinearLayoutManager(activity)
        playerListAdapter = PlayerAdapter(this@ChoosePlayerFragment)
        binding.playerListRecyclerView.adapter = playerListAdapter
        binding.playerListRecyclerView.setHasFixedSize(true)
    }


}


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
import androidx.recyclerview.widget.LinearLayoutManager
import app.lingoknight.database.Player
import app.lingoknight.databinding.FragmentChoosePlayerBinding

// UI fragment for the selection of a player in game section

class ChoosePlayerFragment : Fragment(), PlayerAdapter.Interaction {

    // When an item is selected from the recyclerview this block of code is carried out.
    override fun onItemSelected(position: Int, item: Player) {
        viewModel.charPicked(item, this)
    }

    //sharing of the view model between multiple fragments
    private val viewModel: GameViewModel by activityViewModels()

    private var playerListAdapter = PlayerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChoosePlayerBinding.inflate(inflater)

        // Giving the binding access to the PracticeViewModel
        binding.gameViewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // TODO shared preference the language change
        val lang = arguments?.get("language")
        Log.d("testing", "Practice Fragment - Language: $lang")
        viewModel.language.value = lang as String

        viewModel.language.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords
                viewModel.refreshList()
                Log.d("testing", "Language observer: ${viewModel.language.value}")
                Log.d("testing", "Language observer: ${viewModel.gameWords.value?.size}")
            }
        })


        //initialisation of the recycler view
        initRecyclerView(binding)


        // Observer on the list of words. Updates the player list with the correct score for that player

        viewModel.listOfWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                playerListAdapter.submitList(viewModel.listOfPlayers)
            }
        })

        viewModel.listOfQuestion.observe(viewLifecycleOwner, { words ->
            words?.apply {
            }
        })

        viewModel.listOfPlayers.observe(viewLifecycleOwner, { players ->
            players?.apply {
                playerListAdapter.submitList(viewModel.listOfPlayers)
            }
        })
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentChoosePlayerBinding) {
        binding.playerListRecyclerView.layoutManager = LinearLayoutManager(activity)
        playerListAdapter = PlayerAdapter(this@ChoosePlayerFragment)
        binding.playerListRecyclerView.adapter = playerListAdapter
        binding.playerListRecyclerView.setHasFixedSize(true)
    }
}


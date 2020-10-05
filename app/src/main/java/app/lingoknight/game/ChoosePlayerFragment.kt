package app.lingoknight.game
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.lingoknight.database.Player
import app.lingoknight.databinding.FragmentChoosePlayerBinding


class ChoosePlayerFragment : Fragment(), PlayerAdapter.Interaction {

    override fun onItemSelected(position: Int, item: Player) {
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    private var playerListAdapter = PlayerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentChoosePlayerBinding.inflate(inflater)
        // Giving the binding access to the PracticeViewModel
        binding.gameViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        initRecyclerView(binding)

        viewModel.listOfPlayers.observe(viewLifecycleOwner, { players ->
            players?.apply {
                // what happens to the view on update to listOfPlayers
                playerListAdapter.submitList(viewModel.listOfPlayers)
            }

        })

        binding.button.setOnClickListener {
            Log.d("testing", "Button Clicked: player list: ${viewModel.listOfPlayers.value?.size}")
        }

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








//binding.button.setOnClickListener { view : View ->
//    view.findNavController().navigate(R.id.action_choosePlayerFragment_to_gameFragment)
//}

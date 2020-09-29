package app.lingoknight.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentPracticeMainBinding

class PracticeMainFragment : Fragment() {
    /**
     * Lazily initialize our [PracticeViewModel].
     */
    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the PracticeMainFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPracticeMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel

        binding.btnDetails.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_practiceMainFragment_to_practiceFragment)
        }

        return binding.root
    }


}
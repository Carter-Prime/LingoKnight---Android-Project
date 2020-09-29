package app.lingoknight.practice

import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import app.lingoknight.databinding.FragmentPracticeBinding



class PracticeFragment : Fragment() {

    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentPracticeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel

        return binding.root
    }




}
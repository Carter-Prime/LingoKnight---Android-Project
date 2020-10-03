package app.lingoknight.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.lingoknight.database.Word
import app.lingoknight.databinding.FragmentPracticeMainBinding



class PracticeMainFragment : Fragment() {

    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPracticeMainBinding.inflate(inflater)
        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.wordListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.wordListRecyclerView.setHasFixedSize(true)

        val adapter = WordAdapter()
        binding.wordListRecyclerView.adapter = adapter


        viewModel.listOfWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords
                adapter.setWords(this)
            }

        })



        return binding.root
    }

}


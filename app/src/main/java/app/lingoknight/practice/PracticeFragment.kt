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
import app.lingoknight.databinding.FragmentPracticeBinding




class PracticeFragment : Fragment(), WordAdapter.Interaction {

    override fun onItemSelected(position: Int, item: Word?) {
        Log.d("testing", "1. Clicked: $position and $item the current viewModel word: ${viewModel.word.value?.text}" )
        viewModel.itemClicked(position, item, this)
    }

    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

    private lateinit var wordListAdapter: WordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPracticeBinding.inflate(inflater)
        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        initRecyclerView(binding)

        viewModel.listOfWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords
                wordListAdapter.submitList(viewModel.listOfWords)
            }

        })



        return binding.root
    }

    private fun initRecyclerView(binding: FragmentPracticeBinding){

        binding.wordListRecyclerView.layoutManager = LinearLayoutManager(activity)
        wordListAdapter = WordAdapter(this@PracticeFragment)
        binding.wordListRecyclerView.adapter = wordListAdapter
        binding.wordListRecyclerView.setHasFixedSize(true)

    }

}


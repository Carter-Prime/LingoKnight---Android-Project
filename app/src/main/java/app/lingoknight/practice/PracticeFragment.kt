//Michael Carter
// 1910059

package app.lingoknight.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.lingoknight.database.Word
import app.lingoknight.databinding.FragmentPracticeBinding


class PracticeFragment : Fragment(), WordAdapter.Interaction {

    override fun onItemSelected(position: Int, item: Word) {
        viewModel.word.value = item
        viewModel.itemClicked(this)
    }

    private val viewModel: PracticeViewModel by activityViewModels()

    private lateinit var wordListAdapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPracticeBinding.inflate(inflater)
        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        //TODO fix language transition
        val lang = arguments?.get("language")

        viewModel.language.value = lang as String

        viewModel.language.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords
                viewModel.refreshList()
            }
        })

        viewModel.listOfWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords

                viewModel.refreshList()
                wordListAdapter.submitList(viewModel.practiceWords)
            }
        })

        viewModel.practiceWords.observe(viewLifecycleOwner, { words ->
            words?.apply {
                // what happens to the view on update to listOfWords

                wordListAdapter.submitList(viewModel.practiceWords)
            }
        })

        initRecyclerView(binding)
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentPracticeBinding) {

        binding.wordListRecyclerView.layoutManager = LinearLayoutManager(activity)
        wordListAdapter = WordAdapter(this@PracticeFragment)
        binding.wordListRecyclerView.adapter = wordListAdapter
        binding.wordListRecyclerView.setHasFixedSize(true)

    }

}


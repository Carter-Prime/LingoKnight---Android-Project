package app.lingoknight.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.lingoknight.database.Word
import app.lingoknight.databinding.FragmentPracticeMainBinding



class PracticeMainFragment : Fragment() {

    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listOfWords.observe(viewLifecycleOwner, Observer<List<Word?>> { words ->
            words?.apply {
                //Todo what happens to the view on update
                viewModel.setWord()
            }
        })


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPracticeMainBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the PracticeViewModel
        binding.practiceViewModel = viewModel

        return binding.root
    }

}
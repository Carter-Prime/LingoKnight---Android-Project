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
import timber.log.Timber


class PracticeMainFragment : Fragment() {

    private val viewModel: PracticeViewModel by lazy {
        ViewModelProvider(this).get(PracticeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listOfWords.observe(viewLifecycleOwner, Observer<List<Word>> { words ->
            words?.apply {
                //Todo what happens to the view on update
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
        binding.wordText.text = viewModel.word.value?.text


        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            Toast.makeText(context, viewModel.word.value?.text, Toast.LENGTH_SHORT).show()
        })

        binding.btnDetails.setOnClickListener {
            viewModel.changeWord()
            Toast.makeText(context, viewModel.word.value?.text, Toast.LENGTH_SHORT).show()

        }



        return binding.root
    }

}
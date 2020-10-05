package app.lingoknight.practice

import android.app.Application
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import app.lingoknight.R
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Player
import app.lingoknight.database.Word
import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch
import java.io.IOException



class PracticeViewModel(application: Application) : AndroidViewModel(application) {

    private val wordsRepository = AppRepository(AppDatabase.getInstance(application))

    private var _listOfWords = wordsRepository.words
    val listOfWords: LiveData<List<Word?>>
            get() = _listOfWords


    val word: MutableLiveData<Word?> by lazy {
        MutableLiveData<Word?>()
    }

    init {
        refreshDataFromRepository()
        Log.d("testing", "PracticeView Model Init: ${listOfWords.value?.size}")
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
                wordsRepository.refreshWords()
        }
    }

    fun itemClicked(view: PracticeFragment){
        Log.d("testing", "item Clicked: ${listOfWords.value?.size}")
        view.findNavController().navigate(R.id.action_practiceFragment_to_practiceDetailsFragment)
    }

}


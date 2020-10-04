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
import app.lingoknight.database.Word
import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch
import java.io.IOException



class PracticeViewModel(application: Application) : AndroidViewModel(application) {

    private val wordsRepository = AppRepository(AppDatabase.getInstance(application))

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _listOfWords = wordsRepository.words
    val listOfWords: LiveData<List<Word?>>
            get() = _listOfWords

    private var _word  = wordsRepository.getWordLiveData("king")
    val word: LiveData<Word?>
            get() = _word

    init {
        refreshDataFromRepository()
        Log.d("testing", "${listOfWords.value?.size}")
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try{
                wordsRepository.refreshWords()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException){
                if(listOfWords.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun itemClicked(position: Int, clickedWord: Word?, view: PracticeFragment){
        val bundle = bundleOf("position" to position)
        view.findNavController().navigate(R.id.action_practiceFragment_to_practiceDetailsFragment, bundle)
    }

    fun getWord(position: Int): Word? {
        return listOfWords.value?.get(position)
    }

}


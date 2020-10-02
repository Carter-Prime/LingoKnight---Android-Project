package app.lingoknight.practice

import android.app.Application

import androidx.lifecycle.*
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Word

import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch
import timber.log.Timber

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
    val listOfWords: LiveData<List<Word>>
            get() = _listOfWords

    private var _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word


    init {
        refreshDataFromRepository()
        _word.value = listOfWords.value?.get(1)
        Timber.d(_word.value?.text)
    }


    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try{
                wordsRepository.refreshWords()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch(networkError: IOException){
                // Show a Toast error message and hide the progress bar.
                if(listOfWords.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun changeWord(){
        _word.value = listOfWords.value?.get(5)
    }


}
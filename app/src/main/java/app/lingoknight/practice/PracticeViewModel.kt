package app.lingoknight.practice


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log


import androidx.lifecycle.*
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Word

import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


import java.io.IOException
import java.lang.Math.random
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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

    private var _word = MutableLiveData<Word?>()
    val word: LiveData<Word?>
        get() = _word


    init {
        refreshDataFromRepository()
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


}
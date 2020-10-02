package app.lingoknight.practice


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log


import androidx.lifecycle.*
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Word

import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch


import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class PracticeViewModel(application: Application) : AndroidViewModel(application) {

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

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
        Log.d("testing","init called: text = ${listOfWords.value?.get(0)}")
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
        if(_word.value?.text == listOfWords.value?.get(1)?.text){
            _word.value = listOfWords.value?.get(4)
        }else{
            _word.value = listOfWords.value?.get(1)
        }
        Log.d("testing"," changeWord called: text = ${_word.value?.text}")
    }


    fun setWord(){
        _word.value = listOfWords.value?.get(0)
        Log.d("testing","setWord called: text = ${listOfWords.value?.get(0)}")
    }

}
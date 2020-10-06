// Michael Carter
// 1910059

package app.lingoknight.practice

import android.app.Application
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


// View Model to control all the logic related to the practice aspect of the application.

class PracticeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(AppDatabase.getInstance(application))

    // Master list of words
    private var _listOfWords = repository.words
    val listOfWords: LiveData<List<Word>>
        get() = _listOfWords

    // Selected language to be displayed
    val language: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // This is the list of data used to populate the recyclerView
    val practiceWords: MutableLiveData<List<Word>> by lazy {
        MutableLiveData<List<Word>>()
    }

    // Live data of word for gather details
    val word: MutableLiveData<Word> by lazy {
        MutableLiveData<Word>()
    }

    init {
        refreshDataFromRepository()

    }

    // Coroutine call to check for new data and insert it to database
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            repository.refreshWords()
        }
    }

    fun itemClicked(view: PracticeFragment) {
        view.findNavController().navigate(R.id.action_practiceFragment_to_practiceDetailsFragment)
    }

    //updates practiceWords based on the language selected
    fun refreshList() {
        val list = listOfWords.value?.filter { it.lang == language.value }
        practiceWords.value = list
    }

    // Factory reset of database
    fun hardReset() {
        repository.reset()
        refreshDataFromRepository()
    }

}


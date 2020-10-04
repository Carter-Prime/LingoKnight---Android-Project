package app.lingoknight.game

import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import app.lingoknight.R
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Player
import app.lingoknight.database.Question
import app.lingoknight.database.Word
import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch


class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val wordsRepository = AppRepository(AppDatabase.getInstance(application))

    private var _listOfPlayers = wordsRepository.listOfPlayer
    val listOfPlayers: LiveData<List<Player>>
        get() = _listOfPlayers


    private var _listOfQuestions = wordsRepository.listOfQuestions
    val listOfQuestion: LiveData<List<Question>>
        get() = _listOfQuestions

    private var _listOfWords = wordsRepository.words
    val listOfWords: LiveData<List<Word?>>
        get() = _listOfWords

    private var _word  = wordsRepository.getWordLiveData("king")
    val word: LiveData<Word?>
        get() = _word

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
                wordsRepository.refreshWords()
        }
    }

    fun itemClicked(position: Int, clickedPlayer: Player, view: ChoosePlayerFragment){
        val bundle = bundleOf("position" to position)
        view.findNavController().navigate(R.id.action_practiceFragment_to_practiceDetailsFragment, bundle)
    }

    fun getWord(position: Int): Word? {
        return listOfWords.value?.get(position)
    }

}

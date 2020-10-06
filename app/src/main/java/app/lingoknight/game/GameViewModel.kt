//Michael Carter
// 1910059

package app.lingoknight.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import app.lingoknight.R
import app.lingoknight.database.*
import app.lingoknight.repository.AppRepository
import kotlinx.coroutines.launch


class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val wordsRepository = AppRepository(AppDatabase.getInstance(application))
    private var questionIndex = 0
    private val numberOfQuestions = 5
    lateinit var answerList: List<String>
    var score = 0

    private val gameQuestions: MutableLiveData<List<Question>> by lazy {
        MutableLiveData<List<Question>>()
    }

    val language: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentQuestion: MutableLiveData<Question> by lazy {
        MutableLiveData<Question>()
    }

    val player: MutableLiveData<Player> by lazy {
        MutableLiveData<Player>()
    }


    val gameWords: MutableLiveData<List<Word>> by lazy {
        MutableLiveData<List<Word>>()
    }

    private var _listOfWords = wordsRepository.words
    val listOfWords: LiveData<List<Word>>
        get() = _listOfWords


    private var _listOfPlayers = wordsRepository.listOfPlayer
    val listOfPlayers: LiveData<List<Player>>
        get() = _listOfPlayers


    private var _listOfQuestions = wordsRepository.listOfQuestions
    val listOfQuestion: LiveData<List<Question>>
        get() = _listOfQuestions


    init {
        refreshDataFromRepository()
    }

    // Checking for updates on data from the internet
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            wordsRepository.refreshWords()
        }
    }

    // Player is selected and initialises game
    fun charPicked(item: Player, view: ChoosePlayerFragment) {
        player.value = item
        startGame()
        view.findNavController().navigate(R.id.action_choosePlayerFragment_to_gameFragment)
    }

    // Generates all the questions and initiates the first one.
    private fun startGame() {
        score = 0
        generateQuestionList()
        setQuestion()
        randomAnswers()
    }

    // Randomly selects a selection of question and saves to a livedata list. amount of questions can be modified.
    private fun generateQuestionList() {
        questionIndex = 0
        gameQuestions.value =
            listOfQuestion.value?.filter { question -> question.lang == language.value }?.shuffled()
                ?.take(numberOfQuestions)
        gameWords.value = listOfWords.value?.filter { it.lang == language.value }
    }

    // Increase the word statistics for word seen
    fun increaseSeen() {
        var item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        if (item.isNullOrEmpty()) {
            item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        }
        val number = item[0].wordSeen
        item[0].wordSeen = number.plus(1)
        wordsRepository.updateWord(item[0])
    }

    // Increase the word statistics for word correct
    fun increaseCorrect() {
        var item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        if (item.isNullOrEmpty()) {
            item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        }
        val number = item[0].wordCorrect
        item[0].wordCorrect = number.plus(1)
        wordsRepository.updateWord(item[0])
    }

    // Increase the word statistics for word incorrect
    fun increaseIncorrect() {
        var item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        if (item.isNullOrEmpty()) {
            item = gameWords.value!!.filter { currentQuestion.value!!.answers[0] == it.text }
        }
        val number = item[0].wordIncorrect
        item[0].wordIncorrect = number.plus(1)
        wordsRepository.updateWord(item[0])
    }


    // Sets the current question to view
    private fun setQuestion() {
        currentQuestion.value = gameQuestions.value?.get(questionIndex)
    }

    // Iterates through the questions when requested
    fun progressQuestion() {
        if(!isEnd()){
            questionIndex += 1
            setQuestion()
        }
    }

    // Randomises the  answer order for the set question - index [0] always correct before shuffle
    fun randomAnswers() {
        answerList = currentQuestion.value?.answers?.shuffled()!!
    }


    // Checking if the answer is correct or not
    fun isCorrect(answerIndex: Int): Boolean {
        return currentQuestion.value?.answers?.get(0) == answerList[answerIndex]
    }

    // Checking if the game has ended or not
    fun isEnd(): Boolean {
        return questionIndex == gameQuestions.value?.size?.minus(1)
    }

    // Updates database on the score of the player at the end of the game
    fun updatePlayerScore() {
        val num = player.value?.playerScore
        score += num!!
        player.value?.playerScore = score
        score = 0
        wordsRepository.upDatePlayer(player)
    }

    // Refreshes the games list of words based on the language selected
    fun refreshList() {
        val translatedList = listOfWords.value?.filter { it.lang == language.value }
        gameWords.value = translatedList
    }

}

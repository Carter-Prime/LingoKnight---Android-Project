package app.lingoknight.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var questionIndex = 0
    private val numberOfQuestions = 5
    private val language = "English"
    lateinit var answerList: List<String>

    val currentQuestion: MutableLiveData<Question> by lazy {
        MutableLiveData<Question>()
    }
    private val playerName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val player: MutableLiveData<Player> by lazy {
        MutableLiveData<Player>()
    }

    private val gameQuestions: MutableLiveData<List<Question>> by lazy {
        MutableLiveData<List<Question>>()
    }

    private val gameWords: MutableLiveData<List<Word?>> by lazy {
        MutableLiveData<List<Word?>>()
    }

    private var _listOfWords = wordsRepository.words
    val listOfWords: LiveData<List<Word?>>
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

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
                wordsRepository.refreshWords()
        }
    }

    fun charPicked(position: Int, item: Player, view: ChoosePlayerFragment){
        playerName.value = item.name
        player.value = item
        startGame()
        view.findNavController().navigate(R.id.action_choosePlayerFragment_to_gameFragment)
    }


    private fun startGame(){
        generateQuestionList()
        setQuestion()
        randomAnswers()
    }

    private fun finishGame(){

    }



    private fun generateQuestionList(){
        questionIndex = 0
        gameQuestions.value = listOfQuestion.value?.filter {question -> question.lang == language}?.shuffled()?.take(numberOfQuestions)
        Log.d("testing", "generateQuestionList: ${gameQuestions.value?.forEach { println(it) }}")
    }



    private fun setQuestion(){
        currentQuestion.value = gameQuestions.value?.get(questionIndex)
    }

    fun checkAnswer(){

        questionIndex += 1
        setQuestion()
    }

    fun randomAnswers(){
        answerList = currentQuestion.value?.answers?.shuffled()!!
    }





}

package app.lingoknight.repository


import androidx.lifecycle.LiveData
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Player
import app.lingoknight.database.Word
import app.lingoknight.network.WordApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppRepository(private val database: AppDatabase) {

    val words: LiveData<List<Word?>> = database.wordDao.getListOfWords()
    val listOfQuestions = database.questionDao.getListOfQuestions()
    val listOfPlayer = database.playerDao.getListOfPlayers()

    suspend fun refreshWords(){
        withContext(Dispatchers.IO){

            val networkWordList = WordApi.retrofitService.getWordProperties()
            val networkQuestionList = WordApi.retrofitService.getQuestionProperties()
            val networkPlayersList = WordApi.retrofitService.getPlayerProperties()
            database.wordDao.insertAll(networkWordList)
            database.questionDao.insertAllQuestions(networkQuestionList)
            database.playerDao.insertAllPlayers(networkPlayersList)
        }
    }

    fun getWordLiveData(name: String?): LiveData<Word?> {
        return database.wordDao.getWordLiveData(name)
    }

    fun getPlayer(name: String?): LiveData<Player>{
        return database.playerDao.getPlayer(name)
    }


}
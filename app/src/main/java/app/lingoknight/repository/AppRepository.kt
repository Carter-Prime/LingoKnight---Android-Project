package app.lingoknight.repository


import androidx.lifecycle.LiveData
import app.lingoknight.database.AppDatabase
import app.lingoknight.database.Word
import app.lingoknight.network.WordApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppRepository(private val database: AppDatabase) {

    val words: LiveData<List<Word?>> = database.wordDao.getListOfWords()

    suspend fun refreshWords(){
        withContext(Dispatchers.IO){

            val networkWordList = WordApi.retrofitService.getProperties()
            database.wordDao.insertAll(networkWordList)
        }
    }

    fun getWord(name: String?): Word? {
        return database.wordDao.getWord(name)
    }

    fun getWordLiveData(name: String?): LiveData<Word?> {
        return database.wordDao.getWordLiveData(name)
    }

    fun getListSpecificWords(name: String?): LiveData<List<Word?>>{
        return database.wordDao.getListOfSpecificWords(name)
    }

}
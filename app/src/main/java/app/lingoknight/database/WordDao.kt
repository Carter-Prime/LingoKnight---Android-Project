package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( word: List<Word?>)

    @Update
    fun updateWord(word: Word?)

    @Delete
    fun deleteWord(word: Word?)

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWord(word: String?): Word?

    @Query("SELECT * FROM WORDS_TABLE")
    fun getListOfWords(): LiveData<List<Word?>>

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getListOfSpecificWords(word: String?): LiveData<List<Word?>>

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWordLiveData(word: String?): LiveData<Word?>
}
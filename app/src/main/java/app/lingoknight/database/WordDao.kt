package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(words: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<Word>)

    @Update
    fun updateWord(words: Word)

    @Delete
    fun deleteWord(words: Word)

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word LIMIT 1")
    fun getWord(word: String): LiveData<Word>

    @Query("SELECT * FROM WORDS_TABLE")
    fun getListOfWords(): LiveData<List<Word>>

}
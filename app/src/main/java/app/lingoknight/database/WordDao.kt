//Michael Carter
// 1910059

package app.lingoknight.database

import androidx.lifecycle.LiveData

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(word: List<Word>)

    @Update
    fun updateWord(word: Word)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateAll(word: List<Word>)

    @Delete
    fun deleteWord(word: Word)

    @Query("DELETE FROM words_table")
    fun deleteAllWords()

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWord(word: String?): Word

    @Query("SELECT * FROM WORDS_TABLE")
    fun getListOfWords(): LiveData<List<Word>>

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getListOfSpecificWords(word: String): LiveData<List<Word>>

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWordLiveData(word: String): LiveData<Word>

    @Query("SELECT * FROM WORDS_TABLE WHERE lang == :language")
    fun getListOfWords(language: String?): LiveData<List<Word>>
}
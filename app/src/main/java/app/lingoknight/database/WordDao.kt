package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.room.*
import app.lingoknight.data.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(words: Words)

    @Update
    fun updateWord(words: Words)

    @Delete
    fun deleteWord(words: Words)

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWord(word: String): Words

}
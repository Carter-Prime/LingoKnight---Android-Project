package app.lingoknight.database

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(words: Word)

    @Update
    fun updateWord(words: Word)

    @Delete
    fun deleteWord(words: Word)

    @Query("SELECT * FROM WORDS_TABLE WHERE text = :word")
    fun getWord(word: String): Word

}
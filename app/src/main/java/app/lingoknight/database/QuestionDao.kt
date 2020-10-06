// Michael Carter
// 1910059

package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllQuestions(lisQuestions: List<Question>)

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)

    @Query("SELECT * FROM question_table WHERE wordId = :wordId")
    fun getQuestion(wordId: String): Question

    @Query("SELECT * FROM question_table")
    fun getListOfQuestions(): LiveData<List<Question>>
}
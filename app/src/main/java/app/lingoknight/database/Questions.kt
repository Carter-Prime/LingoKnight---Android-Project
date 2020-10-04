package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(@ColumnInfo val wordId: String, @ColumnInfo val text: String, @PrimaryKey @ColumnInfo val answers: List<String>, @ColumnInfo val lang: String) {

}
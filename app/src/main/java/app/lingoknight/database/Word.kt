//Michael Carter
// 1910059


package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Data class for words

@Entity(tableName = "words_table")
data class Word(
    @ColumnInfo val id: String,
    @ColumnInfo val lang: String,
    @PrimaryKey @ColumnInfo var text: String
) {

    @ColumnInfo
    var wordSeen = 0

    @ColumnInfo
    var wordCorrect = 0

    @ColumnInfo
    var wordIncorrect = 0

    @ColumnInfo
    var translationsList = mutableSetOf<Word>()

    fun addTranslation(t: Word) {
        translationsList.add(t)
    }

}
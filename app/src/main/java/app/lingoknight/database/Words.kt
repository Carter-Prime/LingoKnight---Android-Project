package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Words(@ColumnInfo val lang: String, @ColumnInfo val text: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
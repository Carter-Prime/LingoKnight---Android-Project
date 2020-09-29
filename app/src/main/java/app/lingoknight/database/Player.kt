package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "players_table")
data class Player (@ColumnInfo var playerName: String) {

    @PrimaryKey(autoGenerate = true)
    var playerId: Int = 0

    @ColumnInfo
    var highestScore: Int = 0

    @ColumnInfo
    var score: Int? = 0

    @ColumnInfo
    var position: Int? = 0


}
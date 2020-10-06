// Michael Carter
// 1910059

package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(@PrimaryKey @ColumnInfo var name: String, @ColumnInfo var pictureId: String) {

    @ColumnInfo
    var playerScore = 0

}
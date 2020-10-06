// Michael Carter
// 1910059

package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllPlayers( listOfPlayers: List<Player>)

    @Update
    fun updatePlayer(player: Player?)

    @Query ("DELETE FROM player_table")
    fun deleteAllPlayers()

    @Query("SELECT * FROM player_table WHERE name = :playerName")
    fun getPlayer(playerName: String?): LiveData<Player>

    @Query("SELECT * FROM player_table")
    fun getListOfPlayers(): LiveData<List<Player>>
}
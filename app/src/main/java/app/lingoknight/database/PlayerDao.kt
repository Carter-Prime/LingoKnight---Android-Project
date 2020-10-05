package app.lingoknight.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlayers( listOfPlayers: List<Player>)

    @Update
    fun updatePlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)

    @Query("SELECT * FROM player_table WHERE name = :playerName")
    fun getPlayer(playerName: String?): LiveData<Player>

    @Query("SELECT * FROM player_table")
    fun getListOfPlayers(): LiveData<List<Player>>
}
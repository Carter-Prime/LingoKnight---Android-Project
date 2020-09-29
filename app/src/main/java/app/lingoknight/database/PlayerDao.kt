package app.lingoknight.database

import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Update
    fun updatePlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)

    @Query("DELETE FROM players_table")
    fun deleteAllPlayers()

    @Query("SELECT * FROM players_table WHERE playerName = :key")
    fun getPlayerName(key: String): Player




}
// Michael Carter
// 1910059

package app.lingoknight.database

import android.content.Context
import androidx.room.*

// Database is created using a singleton.

@Database(
    entities = [Word::class, Question::class, Player::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WordConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val wordDao: WordDao
    abstract val questionDao: QuestionDao
    abstract val playerDao: PlayerDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
//
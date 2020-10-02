package app.lingoknight.database

import android.content.Context
import androidx.room.*


@Database(entities = [Word::class], version = 2, exportSchema = false)
@TypeConverters(WordConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val wordDao: WordDao

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
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                 return instance
            }
        }
    }
}
package app.lingoknight

    import android.util.Log
    import androidx.lifecycle.LiveData
    import androidx.room.Room
    import androidx.test.ext.junit.runners.AndroidJUnit4
    import androidx.test.platform.app.InstrumentationRegistry
    import app.lingoknight.database.*
    import org.junit.Assert.assertEquals
    import org.junit.After
    import org.junit.Assert.assertThat
    import org.junit.Before
    import org.junit.Test
    import org.junit.runner.RunWith
    import java.io.IOException


    @RunWith(AndroidJUnit4::class)
    class AppDatabaseTest {

        private lateinit var playerDao: PlayerDao
        private lateinit var wordDao: WordDao
        private lateinit var db: AppDatabase

        @Before
        fun createDb() {
            Log.d("TESTING", "inside createDb")
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            // Using an in-memory database because the information stored here disappears when the
            // process is killed.
            db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
            playerDao = db.playerDao
            wordDao = db.wordDao
        }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        fun insertAndGetPlayer() {
            val player = Player("Fred")
            playerDao.insertPlayer(player)
            val playerFound = playerDao.getPlayerName("Fred")
            assertEquals(playerFound.playerName, "Fred")
        }

        @Test
        @Throws(Exception::class)
        fun insertAndGetWord() {
            val words = Word("English", "king")
            wordDao.insertWord(words)
            val wordFound = wordDao.getWord("king")
            assertEquals(wordFound.text, "king")
        }

        @Test
        @Throws(Exception::class)
        fun addTranslationAndGetTranslation(){
            val correctList = mutableSetOf<Word>(Word("Swedish", "kung"))
            val words = Word("English", "king")
            wordDao.insertWord(words)
            val king = wordDao.getWord("king")
            king.addTranslation(Word("Swedish", "kung"))
            wordDao.updateWord(king)
            assertEquals(king.translationsList, correctList)
        }

        //ToDo convert to livedata listening test
        @Test
        fun getListOfWords(){
            val correctList = listOf<Word>(Word("English", "king"), Word("English", "princess"),
                Word("English", "knight"), Word("Finnish","kuningas"), Word("Finnish","prinsessa"),
                Word("Finnish", "ritari"))

            correctList.forEach { wordDao.insertWord(it)}
            val listOfWords = wordDao.getListOfWords()
            assertEquals(listOfWords, correctList)
        }
    }
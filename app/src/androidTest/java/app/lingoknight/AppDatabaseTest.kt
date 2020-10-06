// Michael Carter
// 1910059

package app.lingoknight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.lingoknight.database.*
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    // Extension function to allow unit testing of Live Data. Makes Room process the queries
    // synchronously rather than lazily on a background thread, resulting in a null return when testing

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    private lateinit var wordDao: WordDao
    private lateinit var playerDao: PlayerDao
    private  lateinit var questionDao: QuestionDao
    private lateinit var db: AppDatabase


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        wordDao = db.wordDao
        playerDao = db.playerDao
        questionDao = db.questionDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() {
        val words = Word("king","English", "king")
        wordDao.insertWord(words)
        val wordFound = wordDao.getWordLiveData("king").blockingObserve()
        assertEquals(wordFound?.text, "king")
    }

    @Test
    @Throws(Exception::class)
    fun addTranslationAndGetTranslation() {
        val correctList = mutableSetOf(Word("king","Swedish", "kung"))
        val words = Word("king","English", "king")
        wordDao.insertWord(words)
        val king = wordDao.getWordLiveData("king").blockingObserve()
        king?.addTranslation(Word("king","Swedish", "kung"))
        if (king != null) {
            wordDao.updateWord(king)
        }
        assertEquals(king?.translationsList, correctList)
    }

    @Test
    fun getListOfWords() {
        val correctList = listOf(
            Word("king","English", "king"), Word("princess","English", "princess"),
            Word("knight","English", "knight"), Word("king","Finnish", "kuningas"), Word("knight","Finnish", "prinsessa"),
            Word("knight","Finnish", "ritari")
        )

        correctList.forEach { wordDao.insertWord(it) }
        val listOfWords = wordDao.getListOfWords().blockingObserve()
        assertEquals(listOfWords, correctList)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPlayer() {
        val player = Player("John","king")
        playerDao.insertPlayer(player)
        val playerFound = playerDao.getPlayer("John").blockingObserve()
        assertEquals(playerFound?.name, "John")
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetQuestion() {
        val question = Question("king", "What is this person called?",answers =  listOf("king", "dragon", "knight", "blue"),"English")
        questionDao.insertQuestion(question)
        val questionFound = questionDao.getQuestion("king")
        assertEquals(questionFound.text, "What is this person called?")
    }
}


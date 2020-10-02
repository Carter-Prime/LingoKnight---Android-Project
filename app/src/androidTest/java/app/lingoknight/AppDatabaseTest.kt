package app.lingoknight

import android.util.Log
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

    /** Extension function to allow unit testing of Live Data. Makes Room process the queries
    synchronously rather than lazily on a background thread, resulting in a null return when testing
    **/
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
        wordDao = db.wordDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() {
        val words = Word("English", "king")
        wordDao.insertWord(words)
        val wordFound = wordDao.getWord("king").blockingObserve()
        assertEquals(wordFound?.text, "king")
    }

    @Test
    @Throws(Exception::class)
    fun addTranslationAndGetTranslation() {
        val correctList = mutableSetOf<Word>(Word("Swedish", "kung"))
        val words = Word("English", "king")
        wordDao.insertWord(words)
        val king = wordDao.getWord("king").blockingObserve()
        king?.addTranslation(Word("Swedish", "kung"))
        wordDao.updateWord(king)
        assertEquals(king?.translationsList, correctList)
    }

    @Test
    fun getListOfWords() {
        val correctList = listOf<Word>(
            Word("English", "king"), Word("English", "princess"),
            Word("English", "knight"), Word("Finnish", "kuningas"), Word("Finnish", "prinsessa"),
            Word("Finnish", "ritari")
        )

        correctList.forEach { wordDao.insertWord(it) }
        val listOfWords = wordDao.getListOfWords().blockingObserve()
        assertEquals(listOfWords, correctList)
    }
}


package app.lingoknight

import app.lingoknight.database.Word
import org.junit.Assert.assertEquals
import org.junit.Test

class WordUnitTests {
    @Test
    fun create() {
        val w = Word("king","Finnish", "kuningas")
        assertEquals(w.text, "kuningas")
        assertEquals(w.lang, "Finnish")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun addTranslationIdentical() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslation(Word("king","English", "king"))
        assertEquals(true, w.isTranslation(Word("king","English", "king")))
    }

    @Test
    fun addTranslationDifferentLanguage() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslation(Word("king","German", "König"))
        assertEquals(false, w.isTranslation(Word("king","Swedish", "Kung")))
    }

    @Test
    fun addTranslationsNonEmpty() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslations(setOf(Word("king","German", "König")))
        assertEquals(true, w.isTranslation(Word("king","German", "König")))
    }

    @Test
    fun addTranslationsEmpty() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslations(setOf())
        assertEquals(false, w.isTranslation(Word("king","Finnish", "kuningas")))
    }

    @Test
    fun addTranslationDifferent() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslation(Word("king","German", "König"))
        assertEquals(false, w.isTranslation(Word("king","Finnish", "kuningas")))
    }

    @Test
    fun translationCountEmpty() {
        val w = Word("king","Finnish", "kuningas")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun translationCountOne() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslation(Word("king","German", "König"))
        assertEquals(1, w.translationCount("German"))
    }

    @Test
    fun translationCountMany() {
        val w = Word("king","Finnish", "kuningas")
        w.addTranslation(Word("king","German", "König"))
        w.addTranslation(Word("king","German", "Kungar"))
        assertEquals(2, w.translationCount("German"))
    }

    @Test
    fun distanceIdentical() {
        val w = Word("king","Finnish", "kuningas")
        assertEquals(0, w.editDistance(Word("king","Finnish", "kuningas")))
    }

    @Test
    fun distanceEmpty1() {
        val w = Word("test","Finnish", "kaivo")
        assertEquals(5, w.editDistance(Word("test","Finnish", "")))
    }

    @Test
    fun distanceEmpty2() {
        val w = Word("test","Finnish", "")
        assertEquals(5, w.editDistance(Word("test","Finnish", "kaivo")))
    }

    @Test
    fun distance1change() {
        val w = Word("test", "Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("test","Finnish", "raivo")))
    }

    @Test
    fun distance1delete() {
        val w = Word("test","Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("test","Finnish", "aivo")))
    }

    @Test
    fun distance1add() {
        val w = Word("test","Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("test","Finnish", "kaivot")))
    }

    @Test
    fun distance1add1delete() {
        val w = Word("test","Finnish", "kaivo")
        assertEquals(2, w.editDistance(Word("test","Finnish", "aivot")))
    }

    @Test
    fun distanceManyAdd() {
        val w = Word("test","Finnish", "kaivo")
        assertEquals(12, w.editDistance(Word("test","Finnish", "vesikaivotkinkaan")))
    }

    @Test
    fun translationsContent() {
        val w = Word("test","abcde", "Finnish")
        w.addTranslation(Word("test","aaaa", "Finnish"))
        w.addTranslation(Word("test","abcd", "Finnish"))
        w.addTranslation(Word("test","abab", "Finnish"))
        w.addTranslation(Word("test","aaaa", "Swedish"))
        w.addTranslation(Word("test","aaaae", "Finnish"))
        assertEquals(true,
            w.isTranslation(Word("test","aaaa", "Finnish"))
                    && w.isTranslation(Word("test","abcd", "Finnish"))
                    && w.isTranslation(Word("test","abab", "Finnish"))
                    && w.isTranslation(Word("test","aaaa", "Swedish"))
                    && w.isTranslation(Word("test","aaaae", "Finnish"))
        )

    }

    @Test
    fun closestTranslationsFew() {
        val w = Word("test","English", "Hello")
        w.addTranslations(setOf(Word("test","Finnish", "Moi"), Word("test","Finnish", "Telve"), Word("test","Finnish", "Hei"),Word("test","Finnish", "Moi moi") ))
        assertEquals("[(Telve, 3), (Hei, 3)]", w.closestTranslations(2,"Finnish").toString())
    }

    @Test
    fun closestTranslationsMany() {
        val w = Word("test","English", "Hello")
        w.addTranslations(setOf(Word("test","Finnish", "Moi"), Word("test","Finnish", "Telve"), Word("test","Finnish", "Hei"),Word("test","Finnish", "Moi moi"), Word("test","Finnish", "Telvetuloa") ))
        assertEquals("[(Telve, 3), (Hei, 3), (Moi, 5), (Moi moi, 6)]", w.closestTranslations(4,"Finnish").toString())
    }


}

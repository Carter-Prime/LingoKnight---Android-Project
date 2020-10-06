//Michael Carter
// 1910059


package app.lingoknight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Data class for words

@Entity(tableName = "words_table")
data class Word(
    @ColumnInfo val id: String,
    @ColumnInfo val lang: String,
    @PrimaryKey @ColumnInfo var text: String
) {

    @ColumnInfo
    var wordSeen = 0

    @ColumnInfo
    var wordCorrect = 0

    @ColumnInfo
    var wordIncorrect = 0

    @ColumnInfo
    var translationsList = mutableSetOf<Word>()

    fun addTranslation(t: Word) {
        translationsList.add(t)
    }

    fun addTranslations(ts: Set<Word>) {
        for(elements in ts){
            translationsList.add(elements)
        }
    }

    fun isTranslation(word: Word): Boolean {
        return translationsList.contains(word)
    }

    fun translationCount(lang: String): Int {
        var count = 0
        for(element in translationsList){
            if (element.lang == lang){
                count += 1
            }
        }
        return count
        // return translations.filter{it.lang == lang}.count()
    }



    fun editDistance(another: Word): Int {
        val strOneLen = this.text.length
        val strTwoLen = another.text.length

        val arr = Array(strOneLen + 1) { IntArray(strTwoLen + 1) }

        if (strOneLen == 0) {
            return strTwoLen
        }
        if (strTwoLen == 0) {
            return strOneLen
        }

        for (row in 0..strOneLen) {
            for (col in 0..strTwoLen) {

                when {
                    row == 0 -> arr[row][col] = col

                    col == 0 -> arr[row][col] = row

                    this.text[row - 1] == another.text[col - 1] -> arr[row][col] = arr[row - 1][col - 1]

                    else -> arr[row][col] = 1 + minOf(arr[row][col - 1], arr[row - 1][col], arr[row - 1][col - 1])
                }
            }
        }
        return arr[strOneLen][strTwoLen]
    }

    fun closestTranslations(numberClosest: Int, lang: String): List<Pair<String, Int>> {
        val closestList = mutableListOf<Pair<String, Int>>()
        translationsList.filter{it.lang == lang}.forEach {closestList.add(Pair(it.text, editDistance(it)))}
        closestList.sortBy { it.second }
        return closestList.filterIndexed{ index, _ -> index < numberClosest}
    }
}
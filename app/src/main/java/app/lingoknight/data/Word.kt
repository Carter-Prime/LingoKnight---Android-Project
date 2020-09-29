package app.lingoknight.data

data class Word(val lang: String, val text: String) {
    private val translations = mutableSetOf<Word>()

    fun addTranslation(t: Word) {
        translations.add(t)
    }

    fun addTranslations(ts: Set<Word>) {
        ts.forEach { translations.add(it)}
    }

    fun isTranslation(word: Word): Boolean {
        return translations.contains(word)
    }

    fun translationCount(lang: String): Int {
        return translations.filter{it.lang == lang}.count()
    }

    private fun editDistance(another: Word): Int {
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
        translations.filter{it.lang == lang}.forEach {closestList.add(Pair(it.text, editDistance(it)))}
        closestList.sortBy { it.second }
        return closestList.filterIndexed{ index, _ -> index < numberClosest}
    }
}
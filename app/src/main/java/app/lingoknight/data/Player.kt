package app.lingoknight.data

data class Player (val int: Int, val playerName: String) {
    private  var highestScore: Int? = null
    private var score: Int? = null
    var position: Int? = null
    var incorrectWords = mutableSetOf<Word>()

}
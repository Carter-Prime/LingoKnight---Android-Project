//Michael Carter
// 1910059

package app.lingoknight.network

import app.lingoknight.database.Player
import app.lingoknight.database.Question
import app.lingoknight.database.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://users.metropolia.fi/~michaejc/LingoKnight/vocabulary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Interface to control the retrieval of data online
interface WordApiService {

    @GET("word2.json")
    suspend fun getWordProperties(): List<Word>

    @GET("questions.json")
    suspend fun getQuestionProperties(): List<Question>

    @GET("players.json")
    suspend fun getPlayerProperties(): List<Player>
}

object WordApi {
    val retrofitService: WordApiService by lazy { retrofit.create(WordApiService::class.java) }
}
package app.lingoknight.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class WordConverter {

    @TypeConverter
    fun translationsToString(setOfWords: Set<Word>?): String? {
        val gson = Gson()
        return gson.toJson(setOfWords)
    }

    @TypeConverter
    fun translationsFromString(jsonString: String): Set<Word>? {
        val gson = Gson()
        val collectionType: Type = object : TypeToken<Set<Word?>?>() {}.type
        return gson.fromJson(jsonString, collectionType)
    }
}
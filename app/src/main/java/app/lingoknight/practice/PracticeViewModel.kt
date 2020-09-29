package app.lingoknight.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import app.lingoknight.network.WordApi
import kotlinx.coroutines.launch

class PracticeViewModel: ViewModel() {

    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    init {
        getWordProperties()
    }

    private fun getWordProperties() {
            viewModelScope.launch {
                try {
                    val listResult = WordApi.retrofitService.getProperties()
                    _response.value = "Success: ${listResult.size} words retrieved"
                } catch (e: Exception) {
                    _response.value = "Failure: ${e.message}"
                }
        }
    }

}
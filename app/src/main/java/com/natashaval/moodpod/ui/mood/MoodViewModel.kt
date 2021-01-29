package com.natashaval.moodpod.ui.mood

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.repository.MoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MoodViewModel @ViewModelInject constructor(private val repository: MoodRepository) :
    ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "I am happy too!"
  }
  val text: LiveData<String> = _text

  private var _mood = MutableLiveData<Mood>()
  val mood: LiveData<Mood> = _mood

  private var _response = MutableLiveData<MyResponse<Mood>>()
  val response: LiveData<MyResponse<Mood>> = _response

  fun setMood(request: Mood) {
    _mood.postValue(request)
  }

  fun setMoodMessage(message: String) {
  }

  fun saveMood(message: String) {
    _mood.value?.message = message
    _response.value = MyResponse.loading()
    CoroutineScope(Dispatchers.IO).launch {
      _response.postValue(repository.saveMood(_mood.value))
    }
  }
}
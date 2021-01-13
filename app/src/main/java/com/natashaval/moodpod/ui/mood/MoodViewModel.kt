package com.natashaval.moodpod.ui.mood

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.repository.mood.MoodRepository

class MoodViewModel @ViewModelInject constructor() :
    ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "I am happy too!"
  }
  val text: LiveData<String> = _text
}
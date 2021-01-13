package com.natashaval.moodpod.ui.mood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoodViewModel : ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is notifications Fragment"
  }
  val text: LiveData<String> = _text
}
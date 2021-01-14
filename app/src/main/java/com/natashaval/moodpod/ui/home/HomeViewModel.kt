package com.natashaval.moodpod.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.model.Quote
import com.natashaval.moodpod.repository.AffirmationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val affirmationRepository: AffirmationRepository
): ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private var _quote = MutableLiveData<MyResponse<Quote>>()
  val quote: LiveData<MyResponse<Quote>> get() = _quote

  fun getQuoteToday() {
    _quote.value = MyResponse.loading()
    CoroutineScope(Dispatchers.IO).launch {
      _quote.postValue(affirmationRepository.getQuoteToday())
    }
  }
}
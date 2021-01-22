package com.natashaval.moodpod.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.model.Quote
import com.natashaval.moodpod.repository.AffirmationRepository
import com.natashaval.moodpod.utils.Constants
import com.natashaval.moodpod.utils.CustomPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class HomeViewModel @ViewModelInject constructor(
    private val affirmationRepository: AffirmationRepository,
    private val sharedPref: CustomPreferences) : ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text

  private var _quote = MutableLiveData<MyResponse<Quote>>()
  val quote: LiveData<MyResponse<Quote>> get() = _quote

  fun getQuoteToday() {
    if (!isDateToday()) {
      _quote.value = MyResponse.loading()
      CoroutineScope(Dispatchers.IO).launch {
        _quote.postValue(affirmationRepository.getQuoteToday())
      }
    } else {
      _quote.postValue(MyResponse.success(Quote(sharedPref.getString(Constants.TODAY_QUOTE_PREF),
          sharedPref.getString(Constants.TODAY_AUTHOR_PREF))))
    }
  }

  private fun isDateToday(): Boolean {
    val savedDate = sharedPref.getString(Constants.TODAY_DATE_PREF)
    val todayDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(
        Calendar.getInstance().timeInMillis)
    Timber.d("MoodLog savedDate: $savedDate, todayDate: $todayDate")
    if (todayDate != savedDate) {
      sharedPref.putString(Constants.TODAY_DATE_PREF, todayDate)
      return false
    }
    return true
  }
}
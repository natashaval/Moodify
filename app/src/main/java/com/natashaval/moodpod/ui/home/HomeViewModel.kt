package com.natashaval.moodpod.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.model.Quote
import com.natashaval.moodpod.repository.AffirmationRepository
import com.natashaval.moodpod.repository.MoodRepository
import com.natashaval.moodpod.utils.Constants
import com.natashaval.moodpod.utils.CustomPreferences
import com.natashaval.moodpod.utils.ViewUtils.convertDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class HomeViewModel @ViewModelInject constructor(
    private val affirmationRepository: AffirmationRepository,
    private val moodRepository: MoodRepository,
    private val sharedPref: CustomPreferences) : ViewModel() {

  private var _quote = MutableLiveData<MyResponse<Quote>>()
  val quote: LiveData<MyResponse<Quote>> get() = _quote

  private var _moodList = MutableLiveData<MyResponse<List<Mood>>>(MyResponse.empty())
  val moodList: LiveData<MyResponse<List<Mood>>> get() = _moodList

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
    val todayDate = Date().convertDate()
    Timber.d("MoodLog savedDate: $savedDate, todayDate: $todayDate")
    if (todayDate != savedDate) {
      sharedPref.putString(Constants.TODAY_DATE_PREF, todayDate)
      return false
    }
    return true
  }

  fun getMoods() {
    CoroutineScope(Dispatchers.IO).launch {
      _moodList.postValue(moodRepository.getMoods())
    }
  }
}
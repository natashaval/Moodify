package com.natashaval.moodpod.ui.mood

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natashaval.moodpod.model.Affirmation
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.repository.AffirmationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by natasha.santoso on 14/01/21.
 */
class AffirmationViewModel @ViewModelInject constructor(
    private val repository: AffirmationRepository) : ViewModel() {
  private var _affirmation = MutableLiveData<MyResponse<Affirmation>>(MyResponse.empty())
  val affirmation: LiveData<MyResponse<Affirmation>> get() = _affirmation

  val handler = CoroutineExceptionHandler { _, throwable ->
    Timber.e("Error: ${throwable.message}")
  }

  fun getAffirmation() {
    CoroutineScope(Dispatchers.IO).launch(handler) {
      _affirmation.postValue(repository.getAffirmation())
    }
  }
}
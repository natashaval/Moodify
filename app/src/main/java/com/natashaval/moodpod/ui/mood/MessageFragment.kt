package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMessageBinding
import com.natashaval.moodpod.util.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MessageFragment : Fragment() {
  private var _binding: FragmentMessageBinding? = null
  private val binding get() = _binding!!
  private val moodViewModel: MoodViewModel by activityViewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMessageBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.tvHelper.text = getString(R.string.message_helper, MESSAGE_MAX_CHARACTERS)
    binding.fabFinish.setSafeClickListener {
      saveMood()
      val action = MessageFragmentDirections.actionMessageFragmentToNavigationHome()
      findNavController().navigate(action)
    }
    setDateTime()
    binding.etMessage.textChanges().debounce(DEBOUNCE_TIME_MILLIS, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          binding.tvHelper.text = getString(R.string.message_helper, MESSAGE_MAX_CHARACTERS - binding.etMessage.length())
        }
  }

  private fun setDateTime() {
    moodViewModel.moodRequest.observe(viewLifecycleOwner, { request ->
      Timber.d("MoodLog message: $request")
      val calendar = Calendar.getInstance()
      calendar.time = request.date
      binding.etDate.setText(DateFormat.getDateInstance().format(calendar.time))
      binding.etTime.setText(getString(R.string.time_detail, calendar.get(Calendar.HOUR_OF_DAY),
          calendar.get(Calendar.MINUTE)))
    })
  }

  private fun saveMood() {
    moodViewModel.setMoodMessage(binding.etMessage.text.toString())
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    private const val DEBOUNCE_TIME_MILLIS = 500L
    private const val MESSAGE_MAX_CHARACTERS = 120
  }
}
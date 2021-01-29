package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.MoodStatus
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.utils.ViewUtils.hideView
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.DateFormat
import java.util.*

@AndroidEntryPoint class MoodFragment : Fragment() {

  private var _binding: FragmentMoodBinding? = null
  private val binding get() = _binding!!
  private val moodViewModel: MoodViewModel by activityViewModels()
  private val affirmationViewModel: AffirmationViewModel by viewModels()
  private var localDate = Calendar.getInstance()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMoodBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // https://stackoverflow.com/questions/51955357/hide-android-bottom-navigation-view-for-child-screens-fragments
    (activity as MainActivity).showBottomNav(false)
    affirmationViewModel.affirmation.observe(viewLifecycleOwner, {
      when(it.status) {
        Status.EMPTY -> affirmationViewModel.getAffirmation()
        Status.SUCCESS -> binding.tvAffirmation.text = it.data?.affirmation
        else -> binding.tvAffirmation.hideView()
      }
    })
    binding.fabNext.setSafeClickListener {
      setMood()
      val action = MoodFragmentDirections.actionNavigationMoodToMessageFragment()
      findNavController().navigate(action)
    }
    setDateTimePicker()
  }

  private fun setDateTimePicker() {
    binding.etDate.setSafeClickListener {
      val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
          .setSelection(Calendar.getInstance().timeInMillis)
      val datePicker = datePickerBuilder.build()
      datePicker.addOnPositiveButtonClickListener {
        binding.etDate.setText(DateFormat.getDateInstance().format(it))
        localDate.timeInMillis = it
        Timber.d("MoodLog localDate: $localDate")
      }
      datePicker.show(childFragmentManager, "MaterialDatePicker")
    }

    binding.etTime.setSafeClickListener {
      val calendar = Calendar.getInstance()
      val timePickerBuilder = MaterialTimePicker.Builder()
          .setTimeFormat(TimeFormat.CLOCK_24H)
          .setHour(calendar.get(Calendar.HOUR_OF_DAY))
          .setMinute(calendar.get(Calendar.MINUTE))
      val timePicker = timePickerBuilder.build()
      timePicker.addOnPositiveButtonClickListener {
        binding.etTime.setText(getString(R.string.time_detail, timePicker.hour, timePicker.minute))
        localDate.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        localDate.set(Calendar.MINUTE, timePicker.minute)
        Timber.d("MoodLog localTime: $localDate")
      }
      timePicker.show(childFragmentManager, "MaterialTimePicker")
    }
  }

  private fun setMood() {
    var mood = ""
    when (binding.itemMood.radioGroupMood.checkedRadioButtonId) {
      R.id.rb_mood_joy -> mood = MoodStatus.Joy.name
      R.id.rb_mood_happy -> mood = MoodStatus.Happy.name
      R.id.rb_mood_neutral -> mood = MoodStatus.Neutral.name
      R.id.rb_mood_sad -> mood = MoodStatus.Sad.name
    }
    val request = Mood(mood, "", localDate.time)
    Timber.d("MoodLog request: $request")
    moodViewModel.setMood(request)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onDetach() {
    super.onDetach()
    (activity as MainActivity).showBottomNav(true)
  }
}
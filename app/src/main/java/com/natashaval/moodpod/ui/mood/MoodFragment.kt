package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jakewharton.rxbinding4.widget.checked
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.MoodStatus
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.utils.DateUtils.convertDate
import com.natashaval.moodpod.utils.DateUtils.convertTime
import com.natashaval.moodpod.utils.DateUtils.dateToCalendar
import com.natashaval.moodpod.utils.ViewUtils.hideView
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint class MoodFragment : Fragment() {

  private var _binding: FragmentMoodBinding? = null
  private val binding get() = _binding!!
  private val affirmationViewModel: AffirmationViewModel by activityViewModels()
  private val args: MoodFragmentArgs by navArgs()
  private var savedDate = Calendar.getInstance()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMoodBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // https://stackoverflow.com/questions/51955357/hide-android-bottom-navigation-view-for-child-screens-fragments
    affirmationViewModel.affirmation.observe(viewLifecycleOwner, {
      when(it.status) {
        Status.EMPTY -> affirmationViewModel.getAffirmation()
        Status.SUCCESS -> binding.tvAffirmation.text = it.data?.affirmation
        else -> binding.tvAffirmation.hideView()
      }
    })
    args.mood?.let {
      setMoodEdit(it)
    }
    binding.fabNext.setSafeClickListener {
      setMood()
    }
    Timber.d("MoodLog localDate: $savedDate")
    setDateTimePicker()
  }

  private fun setDateTimePicker() {
//    https://github.com/material-components/material-components-android/blob/d6761f24e37d09aea87d5a4c972fc1ae146c82a8/catalog/java/io/material/catalog/datepicker/DatePickerMainDemoFragment.java
    val todayDate = Calendar.getInstance()
    binding.etDate.setSafeClickListener {
      val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        .setSelection(todayDate.timeInMillis)
        .setCalendarConstraints(
          CalendarConstraints.Builder()
              .setEnd(todayDate.timeInMillis)
              .setValidator(DateValidatorPointBackward.now())
              .build()
        )
      val datePicker = datePickerBuilder.build()
      datePicker.addOnPositiveButtonClickListener {
        binding.etDate.setText(it.convertDate())
        savedDate.timeInMillis = it
        Timber.d("MoodLog localDate: $savedDate")
      }
      datePicker.show(childFragmentManager, "MaterialDatePicker")
    }

    binding.etTime.setSafeClickListener {
      val timePickerBuilder = MaterialTimePicker.Builder()
          .setTimeFormat(TimeFormat.CLOCK_24H)
          .setHour(todayDate.get(Calendar.HOUR_OF_DAY))
          .setMinute(todayDate.get(Calendar.MINUTE))
      val timePicker = timePickerBuilder.build()
      timePicker.addOnPositiveButtonClickListener {
        binding.etTime.setText(getString(R.string.time_detail, timePicker.hour, timePicker.minute))
        savedDate.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        savedDate.set(Calendar.MINUTE, timePicker.minute)
        Timber.d("MoodLog localTime: $savedDate")
      }
      timePicker.show(childFragmentManager, "MaterialTimePicker")
    }
  }

  private fun setMood() {
    var mood = args.mood?.mood ?: ""
    when (binding.itemMood.radioGroupMood.checkedRadioButtonId) {
      R.id.rb_mood_joy -> mood = MoodStatus.Joy.name
      R.id.rb_mood_happy -> mood = MoodStatus.Happy.name
      R.id.rb_mood_neutral -> mood = MoodStatus.Neutral.name
      R.id.rb_mood_sad -> mood = MoodStatus.Sad.name
      R.id.rb_mood_worry -> mood = MoodStatus.Worry.name
    }
    val request = Mood(mood, args.mood?.message ?: "", savedDate.time, id = args.mood?.id)
    Timber.d("MoodLog request: $request")

    val action = MoodFragmentDirections.actionNavigationMoodToMessageFragment(request)
    findNavController().navigate(action)
  }

  private fun setMoodEdit(mood: Mood) {
    val radioId = resources.getIdentifier("rb_mood_${mood.mood.toLowerCase(Locale.getDefault())}", "id", context?.packageName)
    with(binding) {
      itemMood.radioGroupMood.check(radioId)
      etDate.setText(mood.date.convertDate())
      etTime.setText(mood.date.convertTime())
      savedDate = mood.date.dateToCalendar()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
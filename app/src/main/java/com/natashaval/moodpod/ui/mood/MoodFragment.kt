package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.ui.adapter.OtherMoodAdapter
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
      when (it.status) {
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
    setOtherMoodViewPager()
    setChipGroup(binding.itemChip.cgRelation,
        arrayListOf("family", "partner", "friend", "colleague", "stranger"))
    setChipGroup(binding.itemChip.cgActivity,
        arrayListOf("sleep", "work", "travel", "shopping", "party", "education", "exercise"))
  }

  private fun setDateTimePicker() {
    //    https://github.com/material-components/material-components-android/blob/d6761f24e37d09aea87d5a4c972fc1ae146c82a8/catalog/java/io/material/catalog/datepicker/DatePickerMainDemoFragment.java
    val todayDate = Calendar.getInstance()
    binding.etDate.setSafeClickListener {
      val datePickerBuilder = MaterialDatePicker.Builder.datePicker().setSelection(
          todayDate.timeInMillis).setCalendarConstraints(
          CalendarConstraints.Builder().setEnd(todayDate.timeInMillis).setValidator(
              DateValidatorPointBackward.now()).build())
      val datePicker = datePickerBuilder.build()
      datePicker.addOnPositiveButtonClickListener {
        binding.etDate.setText(it.convertDate())
        savedDate.timeInMillis = it
        Timber.d("MoodLog localDate: $savedDate")
      }
      datePicker.show(childFragmentManager, "MaterialDatePicker")
    }

    binding.etTime.setSafeClickListener {
      val timePickerBuilder = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(
          todayDate.get(Calendar.HOUR_OF_DAY)).setMinute(todayDate.get(Calendar.MINUTE))
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
    val mood = args.mood?.mood ?: getRadioSelector(binding.itemMood.radioGroupMood)
    val request = Mood(mood, args.mood?.message ?: "", savedDate.time, id = args.mood?.id)
    Timber.d("MoodLog request: $request")

    val action = MoodFragmentDirections.actionNavigationMoodToMessageFragment(request)
    findNavController().navigate(action)
  }

  private fun setMoodEdit(mood: Mood) {
    val radioId = resources.getIdentifier("rb_mood_${mood.mood.toLowerCase(Locale.getDefault())}",
        "id", context?.packageName)
    with(binding) {
      itemMood.radioGroupMood.check(radioId)
      etDate.setText(mood.date.convertDate())
      etTime.setText(mood.date.convertTime())
      savedDate = mood.date.dateToCalendar()
    }
  }

  //    https://stackoverflow.com/questions/2597230/loop-through-all-subviews-of-an-android-view
  //    https://stackoverflow.com/questions/10137692/how-to-get-resource-name-from-resource-id
  private fun getRadioSelector(view: ViewGroup): String {
    for (i in 0 .. view.childCount) {
      val child = view.getChildAt(i)
      if (child !is ViewGroup && view is RadioGroup) {
        Timber.d("MoodLog radio selector id: ${child.id}")
        if (child.id == view.checkedRadioButtonId) {
          val name = resources.getResourceEntryName(child.id)
          return name.split("_")[2]
        }
      }
    }
    return ""
  }

  private fun setOtherMoodViewPager() {
    val tabNameArray = resources.getStringArray(R.array.tab_other_mood)
    val otherAdapter = OtherMoodAdapter(this, tabNameArray.size)
    with(binding.itemOtherMood.viewPager) {
      adapter = otherAdapter
      orientation = ViewPager2.ORIENTATION_HORIZONTAL
      registerOnPageChangeCallback(tabPageChangeCallback)
    }

    TabLayoutMediator(binding.itemOtherMood.tabHeader, binding.itemOtherMood.viewPager) { tab, position ->
      tab.text = resources.getStringArray(R.array.tab_other_mood)[position]
    }.attach()
  }

  private var tabPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      Toast.makeText(requireContext(), "Selected position: $position", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setChipGroup(view: ChipGroup, list: ArrayList<String>) {
    context?.let { ctx ->
      list.forEach {
        val chip = Chip(ctx)
        chip.text = it
        view.addView(chip)
      }
    }
  }

  override fun onDestroyView() {
    binding.itemOtherMood.viewPager.unregisterOnPageChangeCallback(tabPageChangeCallback)
    super.onDestroyView()
    _binding = null
  }
}
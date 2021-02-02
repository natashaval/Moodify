package com.natashaval.moodpod.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentHomeBinding
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.ui.adapter.MoodAdapter
import com.natashaval.moodpod.utils.Constants
import com.natashaval.moodpod.utils.CustomPreferences
import com.natashaval.moodpod.utils.DateUtils.convertDate
import com.natashaval.moodpod.utils.DateUtils.dateToCalendar
import com.natashaval.moodpod.utils.ViewUtils.hideView
import com.natashaval.moodpod.utils.DateUtils.parseISODate
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import com.natashaval.moodpod.utils.ViewUtils.showView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private val homeViewModel: HomeViewModel by viewModels()
  private lateinit var rotateAnim: Animation

  @Inject lateinit var sharedPref: CustomPreferences
  private var todayDate: Date? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentHomeBinding.inflate(inflater, container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity as MainActivity).showBottomNav(true)
    rotateAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)

    getQuoteToday()
    setMoodAdapter()
    setMonthButton()
  }

  private fun getQuoteToday() {
    homeViewModel.getQuoteToday()
    homeViewModel.quote.observe(viewLifecycleOwner, Observer {
      when (it.status) {
        Status.LOADING -> {
          handleQuoteLoading(true)
        }
        Status.SUCCESS -> {
          handleQuoteLoading(false)
          binding.itemQuote.tvQuote.showView()
          binding.itemQuote.tvAuthor.showView()
          it.data?.let { q ->
            binding.itemQuote.tvQuote.text = getString(R.string.quote, q.quote)
            binding.itemQuote.tvAuthor.text = q.author
            sharedPref.putString(Constants.TODAY_QUOTE_PREF, q.quote)
            sharedPref.putString(Constants.TODAY_AUTHOR_PREF, q.author)
          }
        }
        else -> {
          handleQuoteLoading(false)
          binding.itemQuote.tvQuote.hideView()
          binding.itemQuote.tvAuthor.hideView()
        }
      }
    })
  }

  private fun handleQuoteLoading(isShow: Boolean = false) {
    if (isShow) {
      binding.itemQuote.ivLoading.showView()
      binding.itemQuote.ivLoading.startAnimation(rotateAnim)
    } else {
      binding.itemQuote.ivLoading.hideView()
      binding.itemQuote.ivLoading.clearAnimation()
    }
  }

  private fun setMoodAdapter() {
    homeViewModel.getMoods()
    homeViewModel.moodList.observe(viewLifecycleOwner, {
      when (it.status) {
        Status.SUCCESS -> {
          it.data?.let { moodList ->
            with(binding.rvMood) {
              showView()
              context?.let { ctx ->
                adapter = MoodAdapter(ctx, moodList)
                layoutManager = LinearLayoutManager(ctx)
              }
            }
          }
        }
        else -> binding.rvMood.hideView()
      }
    })
  }

  private fun setMonthButton() {
    with(binding.itemMonth) {
      todayDate = sharedPref.getString(Constants.TODAY_DATE_PREF).parseISODate()
      updateMonthYear(0)
//      https://stackoverflow.com/questions/16392892/how-to-reduce-one-month-from-current-date-and-stored-in-date-variable-using-java
      btPrevMonth.setSafeClickListener {
        updateMonthYear(-1)
      }
      btNextMonth.setSafeClickListener {
        updateMonthYear(1)
      }
      val firstDate = Calendar.getInstance()
      firstDate.set(Calendar.DAY_OF_MONTH, 1)
      val lastDate = Calendar.getInstance()
      lastDate.set(Calendar.DAY_OF_MONTH, lastDate.getActualMaximum(Calendar.DAY_OF_MONTH))
      binding.itemMonth.tvStartEnd.text = getString(R.string.history_range, firstDate.time.convertDate(), lastDate.time.convertDate())
      btMonthYear.setSafeClickListener {
        showDateRangePicker(Calendar.getInstance())
      }
    }
  }

  private fun updateMonthYear(value: Int) {
    val calendar = todayDate?.dateToCalendar()
    calendar?.add(Calendar.MONTH, value)
    todayDate = calendar?.time
    val split = todayDate?.convertDate()?.split(" ")
    binding.itemMonth.btMonthYear.text = "${split?.get(0)} ${split?.get(2)}"
  }

  private fun showDateRangePicker(todayDate: Calendar) {
    val datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
      .setSelection(Pair(todayDate.timeInMillis, todayDate.timeInMillis))
    val datePicker = datePickerBuilder.build()
    datePicker.addOnPositiveButtonClickListener {
      binding.itemMonth.tvStartEnd.text = getString(R.string.history_range, it.first?.convertDate(), it.second?.convertDate())
      Timber.d("MoodLog startDate: ${it.first?.convertDate()} endDate: ${it.second?.convertDate()}")
    }
    datePicker.show(childFragmentManager, "MaterialDateRangePicker")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
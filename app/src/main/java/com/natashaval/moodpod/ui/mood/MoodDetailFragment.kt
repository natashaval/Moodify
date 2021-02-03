package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.databinding.FragmentMoodDetailBinding
import com.natashaval.moodpod.utils.DateUtils.convertDate
import com.natashaval.moodpod.utils.DateUtils.convertDateTime
import com.natashaval.moodpod.utils.DateUtils.convertTime
import com.natashaval.moodpod.utils.DateUtils.dateToCalendar
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class MoodDetailFragment : Fragment() {
  private var _binding: FragmentMoodDetailBinding? = null
  private val binding get() = _binding!!
  private val args: MoodDetailFragmentArgs by navArgs()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMoodDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity as MainActivity).showBottomNav(false)
    setMoodDetail()
  }

  private fun setMoodDetail() {
    args.moodItem?.let {
      with(binding) {
        Timber.d("MoodLog message: $it")
        tvDate.text = "${it.date.convertDate()}"
        tvTime.text = "${it.date.convertTime()}"
        tvTimeAgo.text = DateUtils.getRelativeTimeSpanString(it.date.time)
        tvMessage.text = it.message

        val moodStatus = it.mood.toLowerCase(Locale.getDefault())
        val moodRes = resources.getIdentifier("ic_${moodStatus}_filled", "drawable",
            context?.packageName)
        if (moodRes == 0) {
          ivMood.setImageResource(R.drawable.ic_emoticon_filled)
        } else {
          ivMood.setImageResource(moodRes)
        }
      }
    }
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
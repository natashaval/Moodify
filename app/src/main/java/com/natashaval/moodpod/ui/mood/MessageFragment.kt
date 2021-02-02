package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding4.widget.textChanges
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMessageBinding
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.utils.DateUtils.convertDate
import com.natashaval.moodpod.utils.DateUtils.convertTime
import com.natashaval.moodpod.utils.DateUtils.dateToCalendar
import com.natashaval.moodpod.utils.ViewUtils.hideView
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint class MessageFragment : Fragment() {
  private var _binding: FragmentMessageBinding? = null
  private val binding get() = _binding!!
  private val moodViewModel: MoodViewModel by activityViewModels()
  private val affirmationViewModel: AffirmationViewModel by activityViewModels()
  private val args: MessageFragmentArgs by navArgs()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMessageBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.tvHelper.text = getString(R.string.message_helper, MESSAGE_MAX_CHARACTERS)
    affirmationViewModel.affirmation.observe(viewLifecycleOwner, {
      when(it.status) {
        Status.SUCCESS -> binding.tvAffirmation.text = it.data?.affirmation
        else -> binding.tvAffirmation.hideView()
      }
    })
    binding.fabFinish.setSafeClickListener {
      args.mood?.let {
        it.message = binding.etMessage.text.toString()
        moodViewModel.saveMood(it)
      }
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
    args.mood?.let {
      with(binding) {
        Timber.d("MoodLog message: $it")
        etMessage.setText(it.message)
        etDate.setText(it.date.convertDate())
        etTime.setText(it.date.convertTime())

        val moodStatus = it.mood.toLowerCase(Locale.getDefault())
        val moodRes = resources.getIdentifier(
          "ic_${moodStatus}_filled", "drawable",
          context?.packageName
        )
        if (moodRes == 0) {
          ivMoodToday.setImageResource(R.drawable.ic_emoticon_filled)
        } else {
          ivMoodToday.setImageResource(moodRes)
        }
      }
    }
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
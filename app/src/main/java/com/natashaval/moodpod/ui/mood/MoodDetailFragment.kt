package com.natashaval.moodpod.ui.mood

import android.content.DialogInterface
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.databinding.FragmentMoodDetailBinding
import com.natashaval.moodpod.utils.DateUtils.convertDate
import com.natashaval.moodpod.utils.DateUtils.convertDateTime
import com.natashaval.moodpod.utils.DateUtils.convertTime
import com.natashaval.moodpod.utils.DateUtils.dateToCalendar
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class MoodDetailFragment : Fragment() {
  private var _binding: FragmentMoodDetailBinding? = null
  private val binding get() = _binding!!
  private val args: MoodDetailFragmentArgs by navArgs()
  private val moodViewModel: MoodViewModel by activityViewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMoodDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setMoodDetail()
  }

  private fun setMoodDetail() {
    args.moodItem?.let {
      with(binding) {
        Timber.d("MoodLog message: $it")
        tvDate.text = it.date.convertDate()
        tvTime.text = it.date.convertTime()
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

        itemEdit.btDelete.setSafeClickListener {
          showDeleteDialog(it.id ?: "")
        }
        itemEdit.btEdit.setSafeClickListener {
          val action = MoodDetailFragmentDirections.actionMoodDetailFragmentToNavigationMood(it)
          findNavController().navigate(action)
        }
      }
    }
  }

  private fun showDeleteDialog(id: String) {
    context?.let {
      MaterialAlertDialogBuilder(it)
        .setTitle("Delete")
        .setMessage("Are you sure to delete?")
        .setNegativeButton("cancel"
        ) { dialog, _ -> dialog.dismiss() }
        .setPositiveButton("delete") { dialog, _ ->
          moodViewModel.deleteMood(id)
          dialog.dismiss()
          findNavController().popBackStack()
        }.show()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
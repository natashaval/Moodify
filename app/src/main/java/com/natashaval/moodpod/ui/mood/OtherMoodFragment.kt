package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentOtherMoodBinding

class OtherMoodFragment : Fragment() {
  private var _binding: FragmentOtherMoodBinding? = null
  private val binding get() = _binding!!
  private val moodLove = arrayOf<String>("calm", "starstruck", "blush", "kiss")
  private var tabName = ""

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentOtherMoodBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    arguments?.let {
      val position = it.getInt(MOOD_POSITION_KEY)
      tabName = resources.getStringArray(R.array.tab_other_mood)[position]
    }
    binding.tvOtherMood.text = tabName
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    private const val MOOD_POSITION_KEY = "mood_position"

    fun newInstance(position: Int) = OtherMoodFragment().apply {
      arguments = Bundle().apply {
        putInt(MOOD_POSITION_KEY, position)
      }
    }
  }
}
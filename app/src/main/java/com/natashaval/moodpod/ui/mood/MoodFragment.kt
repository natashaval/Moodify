package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.util.ViewUtils.setSafeClickListener

class MoodFragment : Fragment() {

  private var _binding: FragmentMoodBinding? = null
  private val binding get() = _binding!!
  private lateinit var moodViewModel: MoodViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    moodViewModel = ViewModelProviders.of(this).get(MoodViewModel::class.java)
    _binding = FragmentMoodBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      fabNext.setSafeClickListener {

      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
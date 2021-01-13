package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMoodBinding
import com.natashaval.moodpod.util.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoodFragment : Fragment() {

  private var _binding: FragmentMoodBinding? = null
  private val binding get() = _binding!!
  private lateinit var moodViewModel: MoodViewModel
  private var moodMap = mapOf<Int, Boolean>(
    MOOD_JOY to false,
    MOOD_HAPPY to false,
    MOOD_NEUTRAL to false,
    MOOD_SAD to false
  ).withDefault { false }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    moodViewModel = ViewModelProviders.of(this).get(MoodViewModel::class.java)
    _binding = FragmentMoodBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // https://stackoverflow.com/questions/51955357/hide-android-bottom-navigation-view-for-child-screens-fragments
    (activity as MainActivity).showBottomNav(false)
    binding.fabNext.setSafeClickListener {
      val action = MoodFragmentDirections.actionNavigationMoodToMessageFragment()
      findNavController().navigate(action)
    }

    moodViewModel.text.observe(viewLifecycleOwner, {
      binding.tvTitle.text = it
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onDetach() {
    super.onDetach()
    (activity as MainActivity).showBottomNav(true)
  }

  companion object {
    private const val MOOD_JOY = 1
    private const val MOOD_HAPPY = 2
    private const val MOOD_NEUTRAL = 3
    private const val MOOD_SAD = 4
  }
}
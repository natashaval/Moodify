package com.natashaval.moodpod.ui.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentMessageBinding
import com.natashaval.moodpod.util.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : Fragment() {
  private var _binding: FragmentMessageBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentMessageBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.fabFinish.setSafeClickListener {
      val action = MessageFragmentDirections.actionMessageFragmentToNavigationHome()
      findNavController().navigate(action)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
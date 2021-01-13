package com.natashaval.moodpod.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

  private lateinit var dashboardViewModel: DashboardViewModel
  private var _binding: FragmentDashboardBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
      binding.textDashboard.text = it
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
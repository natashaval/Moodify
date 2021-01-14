package com.natashaval.moodpod.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.natashaval.moodpod.MainActivity
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.FragmentHomeBinding
import com.natashaval.moodpod.model.Status
import com.natashaval.moodpod.utils.ViewUtils.hideView
import com.natashaval.moodpod.utils.ViewUtils.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private val homeViewModel: HomeViewModel by viewModels()
  private lateinit var rotateAnim: Animation

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = FragmentHomeBinding.inflate(inflater, container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity as MainActivity).showBottomNav(true)
    homeViewModel.text.observe(viewLifecycleOwner, Observer {
      binding.textHome.text = it
    })
    rotateAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)

    getQuoteToday()
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
          binding.itemQuote.tvQuote.text = getString(R.string.quote, it.data?.quote)
          binding.itemQuote.tvAuthor.text = it.data?.author
        }
        Status.ERROR, Status.FAILED -> {
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
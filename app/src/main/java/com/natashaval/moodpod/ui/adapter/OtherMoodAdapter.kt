package com.natashaval.moodpod.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.natashaval.moodpod.ui.mood.OtherMoodFragment

/**
 * Created by natasha.santoso on 07/02/21.
 */
//https://www.raywenderlich.com/8192680-viewpager2-in-android-getting-started
class OtherMoodAdapter(fragment: Fragment, private val itemSize: Int) :
    FragmentStateAdapter(fragment) {
  override fun getItemCount(): Int = itemSize

  override fun createFragment(position: Int): Fragment {
    return OtherMoodFragment.newInstance(position)
  }
}
package com.natashaval.moodpod.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natashaval.moodpod.R
import com.natashaval.moodpod.databinding.ItemMoodBinding
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.utils.ViewUtils.convertDate
import com.natashaval.moodpod.utils.ViewUtils.convertTime
import java.util.*

/**
 * Created by natasha.santoso on 29/01/21.
 */
class MoodAdapter(private val context: Context?, private val moodList: List<Mood>) :
    RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {
  inner class MoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMoodBinding.bind(view)

    fun bind(mood: Mood) {
      with(binding) {
        context?.let { ctx ->
          val moodStatus = mood.mood.toLowerCase(Locale.getDefault())
          val moodRes = ctx.resources.getIdentifier("ic_${moodStatus}_filled", "drawable",
              ctx.packageName)
          ivMood.setImageResource(moodRes)
        }
        tvDate.text = mood.date.convertDate()
        tvTime.text = mood.date.convertTime()
        tvMessage.text = mood.message
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
    return MoodViewHolder(view)
  }

  override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
    holder.bind(moodList[position])
  }

  override fun getItemCount(): Int = moodList.size
}
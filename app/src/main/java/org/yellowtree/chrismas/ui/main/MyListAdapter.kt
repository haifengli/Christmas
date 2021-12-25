package org.yellowtree.chrismas.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.chrismas.R

class MyListAdapter : ListAdapter<TimerItem,  TimerViewHolder>( object : DiffUtil.ItemCallback<TimerItem>() {
    override fun areItemsTheSame(oldItem: TimerItem, newItem: TimerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimerItem, newItem: TimerItem): Boolean {
        return oldItem.startTime == newItem.startTime
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val timerView = LayoutInflater.from(parent.context).inflate(R.layout.timer,  parent, false)
        return TimerViewHolder(timerView)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class TimerViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    fun bind(timer : TimerItem) {
        with(itemView) {
            val timerName = findViewById<TextView>(R.id.timer_name_txt)
            timerName.text = "Timer " + timer.id
        }

    }

}
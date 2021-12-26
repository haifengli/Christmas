package org.yellowtree.chrismas.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.chrismas.R
import java.util.concurrent.TimeUnit

class MyListAdapter : RecyclerView.Adapter<TimerViewHolder>() {

    private var size = 0
    private var visibleItems = mapOf<Int, TimerItem?>()
    private var visibleItemRange = IntRange.EMPTY
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val timerView = LayoutInflater.from(parent.context).inflate(R.layout.timer,  parent, false)
        return TimerViewHolder(timerView)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    fun setVisibleArea(timerRange: IntRange, visibleItems : Map<Int, TimerItem?>) {
        this.visibleItems = visibleItems
        visibleItemRange = timerRange
        refreshVisibleArea()

    }

    fun refreshVisibleArea() {
        notifyItemRangeChanged(visibleItemRange.first, visibleItemRange.count())
        //notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setSize(newSize : Int) {
        if (newSize != size) {
            size = newSize
            notifyDataSetChanged()
        }
    }

   private fun getItem(position : Int) : TimerItem? {
       return visibleItems[position]
   }

    override fun getItemCount(): Int {
        return size
    }


}

class TimerViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    fun bind(timer : TimerItem) {
        with(itemView) {
            val timerName = findViewById<TextView>(R.id.timer_name_txt)
            ("Timer " + timer.id).also { timerName.text = it }
            val timerTime = findViewById<TextView>(R.id.timer_time_txt)
            timerTime.text = generateTimer(System.currentTimeMillis() - timer.startTime)
        }

    }

}

private fun generateTimer(milliseconds : Long) : String {
    return String.format("%02d:%02d.%03d",
        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1),
        TimeUnit.MILLISECONDS.toMillis(milliseconds) % TimeUnit.SECONDS.toMillis(1)
    )
}
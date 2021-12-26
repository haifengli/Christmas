package org.yellowtree.chrismas.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.chrismas.R
import java.util.concurrent.TimeUnit


class MyListAdapter : RecyclerView.Adapter<TimerViewHolder>() {

    private var size = 0
    private var visibleItems = mapOf<Int, TimerItem?>()

    fun updateList(size: Int?, visibleItems: Map<Int, TimerItem?>?) {
        size?.let { this.size = it }
        visibleItems?.let { this.visibleItems = it }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val timerView = LayoutInflater.from(parent.context).inflate(R.layout.timer,  parent, false)
        return TimerViewHolder(timerView)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        getItemByPosition(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return size
    }

    private fun getItemByPosition(position: Int) = visibleItems[position]

}

class TimerViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    fun bind(timer : TimerItem) {
        with(itemView) {
            findViewById<TextView>(R.id.timer_name_txt).apply {
                text = "Timer " + timer.id
            }
            findViewById<TextView>(R.id.timer_time_txt).apply {
                text = generateTimeDiffString(System.currentTimeMillis() - timer.startTime)
            }
        }
    }
}

private fun generateTimeDiffString(millis: Long) = String.format(
    "%02d:%02d.%03d",
    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
    TimeUnit.MILLISECONDS.toMillis(millis) % TimeUnit.SECONDS.toMillis(1)
)

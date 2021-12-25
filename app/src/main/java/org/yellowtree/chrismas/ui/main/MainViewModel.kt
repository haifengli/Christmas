package org.yellowtree.chrismas.ui.main

import android.animation.TimeInterpolator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _timerItems = MutableLiveData<List<TimerItem>>()
    private var timerList : MutableList<TimerItem> = MutableList<TimerItem>(20) { i -> TimerItem(i) }
    private var id : Int = 20

    val map = HashMap<Int, Long>()

    init {
        _timerItems.value = timerList
    }
    val timerItems : LiveData<List<TimerItem>>
        get() = _timerItems




    fun addTimers() {
        var newList = ArrayList(timerList)

        for( i in 0 .. 10) {
            newList.add(TimerItem(id++))
        }
        timerList = newList
        _timerItems.value = newList
    }


}
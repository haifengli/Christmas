package org.yellowtree.chrismas.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {


    private val _size = MutableLiveData<Int>(1)
    val size: LiveData<Int>
        get() = _size





    private val completeTimerMap = mutableMapOf(0 to TimerItem(0))

    private val _visibleTimerMap = MutableLiveData<Pair<IntRange, Map<Int, TimerItem?>>>()

    private var visibleTimerRange : IntRange = IntRange.EMPTY

    val visibleArea : LiveData<Pair<IntRange, Map<Int, TimerItem?>>>
        get() = _visibleTimerMap




    fun addTimers() {
        _size.value = (_size.value ?: 1) * 2

    }

    fun updateVisibleArea(range : IntRange) {
        range.forEach {
            if (!completeTimerMap.containsKey(it)) {
                completeTimerMap[it] = TimerItem(it)
            }
        }
        visibleTimerRange = range
        _visibleTimerMap.value = range to visibleTimerRange.map {
            it to completeTimerMap[it]
        }.toMap()

    }


}
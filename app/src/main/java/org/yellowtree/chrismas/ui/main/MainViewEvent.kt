package org.yellowtree.chrismas.ui.main

sealed class MainViewEvent

object UserSeeLastItem : MainViewEvent()

data class VisibleItemRangeChanged(val range: IntRange) : MainViewEvent()

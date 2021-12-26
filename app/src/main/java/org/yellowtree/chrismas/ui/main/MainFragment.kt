package org.yellowtree.chrismas.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.yellowtree.chrismas.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val TIMER_DELAY = 1000L / 30L
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var rv : RecyclerView
    private lateinit var myAdapter : MyListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private var visibleItemRange  = IntRange.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        rv = view.findViewById<RecyclerView>(R.id.my_rv)

        with(rv) {
            myAdapter = MyListAdapter()
            adapter = myAdapter
            myLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = myLayoutManager
        }



        rv.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (myLayoutManager.findLastCompletelyVisibleItemPosition() == myLayoutManager.itemCount - 1) {
                    viewModel.addTimers()
                }

            }

        })


        val scope = MainScope()
        scope.launch {
            while (true) {
                val curRange = myLayoutManager.findFirstVisibleItemPosition()..myLayoutManager.findLastVisibleItemPosition()
                if (curRange != visibleItemRange) {
                    visibleItemRange = curRange
                    viewModel.updateVisibleArea(curRange)
                }

                updateUI()
                delay(TIMER_DELAY)

            }
        }

        viewModel.size.observe(viewLifecycleOwner, Observer {
            myAdapter.setSize(it)
        })


        viewModel.visibleArea.observe(viewLifecycleOwner, Observer {
            myAdapter.setVisibleArea(it.first, it.second)
        })

    }


    private fun updateUI() {
        myAdapter.refreshVisibleArea()
    }



}
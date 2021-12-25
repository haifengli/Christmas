package org.yellowtree.chrismas.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var rv : RecyclerView
    private lateinit var myAdapter : MyListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager

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

        viewModel.timerItems.observe(viewLifecycleOwner, Observer { listItem ->
            myAdapter.submitList(listItem)

        })

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
                val topPos = myLayoutManager.findFirstVisibleItemPosition()
                val bottomPos = myLayoutManager.findLastVisibleItemPosition()
                if (topPos in 0 until bottomPos) {

                    for( pos in topPos .. bottomPos) {

                        val vh = rv.findViewHolderForAdapterPosition(pos)
                        val item = myAdapter.currentList[pos]

                        with(vh as TimerViewHolder) {
                            val timerValueTxt = itemView.findViewById<TextView>(R.id.timer_time_txt)
                            if (viewModel.map[item.id] == null) {
                                viewModel.map[item.id] = System.currentTimeMillis()
                            } else {
                                val timeValue =
                                    (System.currentTimeMillis() - viewModel.map[item.id]!!) / 1000
                                timerValueTxt.text = timeValue.toString()

                            }

                        }
                    }
                }

                Log.d("Test", "Top pos: $topPos; bottom pos: $bottomPos")
                delay(100)

            }
        }

    }



}
package com.example.customviews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.test_item.view.*
import timber.log.Timber

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    var items= listOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.test_item,parent,false)
        return TestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {

        holder.number.text= "$position"
        holder.content.text= "${items[position]}"//            } else {
//                outRect.set(0, 0, divider.getIntrinsicWidth(), 0)
//            }

    }

    override fun onBindViewHolder(
        holder: TestViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isNotEmpty()) {
            if (payloads[0] is Long) {
                holder.content.text="${payloads[0]}"
            }
        }else {
            super.onBindViewHolder(holder,position, payloads)
        }
    }



    fun updateItems(items:List<Long>){
        Log.i("TAG","updateItems, items: ${items.size}")
        this.items=items
        for ((i, it) in items.withIndex()) {
            notifyItemChanged(i,it)
        }
    }


    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number=view.numberTv
        val content=view.contentTv
    }


}

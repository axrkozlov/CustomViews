package com.example.customviews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.test_item.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customviews.customview.GroupDividerItemDecoration2


class Test3Adapter : RecyclerView.Adapter<Test3Adapter.Test3ViewHolder>() {

    var items = listOf<Long>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test3ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return Test3ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Test3ViewHolder, position: Int) {

        holder.number.text = "$position"
        holder.content.text = "${items[position]}"
        if (position % 3 == 0)
            holder.view.setTag(R.string.groupDividerItemDecorationTag, true)
        else holder.view.setTag(R.string.groupDividerItemDecorationTag, false)
        Log.i("TAG", "$position")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val topDivider = ContextCompat.getDrawable(recyclerView.context, R.drawable.topdivider)
        val footerDivider = ContextCompat.getDrawable(recyclerView.context, R.drawable.divider)
        val itemDecoration = GroupDividerItemDecoration2(
            recyclerView.context,
            LinearLayoutManager.VERTICAL
        )
        itemDecoration.setTopDivider(topDivider!!)
        itemDecoration.setBottomDivider(footerDivider!!)


        recyclerView.addItemDecoration(itemDecoration)


    }

    fun updateItems(items: List<Long>) {
        this.items = items
        notifyDataSetChanged()
    }


    class Test3ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val number = view.numberTv
        val content = view.contentTv
    }







}

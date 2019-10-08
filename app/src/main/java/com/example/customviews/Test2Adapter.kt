package com.example.customviews

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.test_item.view.*
import android.graphics.Rect


class Test2Adapter : RecyclerView.Adapter<Test2Adapter.Test2ViewHolder>() {

    var items = listOf<Long>()


    companion object {
        enum class InGroupPosition {
            HEADER,
            MIDDLE,
            FOOTER
        }

        const val IN_GROUP_TAG = 454
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test2ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return Test2ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Test2ViewHolder, position: Int) {

        holder.number.text = "$position"
        holder.content.text = "${items[position]}"
        if (position % 3 == 0)
            holder.view.setTag(R.string.groupDividerItemDecorationTag, InGroupPosition.FOOTER)
        else holder.view.setTag(R.string.groupDividerItemDecorationTag, InGroupPosition.MIDDLE)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val footerDivider = ContextCompat.getDrawable(recyclerView.context, R.drawable.divider)
        recyclerView.addItemDecoration(InGroupDecoration(divider = footerDivider!!))
    }

    fun updateItems(items: List<Long>) {
        this.items = items
        notifyDataSetChanged()
    }


    class Test2ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val number = view.numberTv
        val content = view.contentTv
    }


    class InGroupDecoration(private val strokeWidth: Float = 45.5f, val divider: Drawable) :
        RecyclerView.ItemDecoration() {

        var paint = Paint()

        init {
            paint.strokeWidth = strokeWidth
        }

        private val mBounds = Rect()
        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

//            for (i in 0 until parent.childCount) {
//
//                val view = parent.getChildAt(i)
//
//
//                if (view.getTag(R.string.InGroupTag)== InGroupPosition.FOOTER) {
//
////                    val dimen = max(view.right - view.left, view.bottom - view.top)
//                    val params = view.getLayoutParams() as RecyclerView.LayoutParams
//
//
////                    c.drawLine(parent.left.toFloat(), view.bottom.toFloat(), parent.right.toFloat() , view.bottom.toFloat(), paint)
////
////                    selectionView.setBounds(view.left, view.top, view.left + dimen, view.top + dimen)
////                    selectionView.draw(c)
//                    val top = view.bottom + params.bottomMargin
//                    val bottom = top + divider.intrinsicHeight
//
//                    divider.setBounds(left, top, right, bottom)
//                    divider.draw(canvas)
//                }

            canvas.save()
            val left: Int
            val right: Int
            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
            if (parent.clipToPadding) {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                canvas.clipRect(
                    left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom
                )
            } else {
                left = 0
                right = parent.width
            }

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
            canvas.restore()



            super.onDraw(canvas, parent, state)
        }

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (divider == null) {
                outRect.set(0, 0, 0, 0)
                return
            }
//            if (mOrientation == VERTICAL) {
                outRect.set(0, 0, 0, divider.getIntrinsicHeight())
//            } else {
//                outRect.set(0, 0, divider.getIntrinsicWidth(), 0)
//            }
        }
    }


}

package com.example.customviews.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.customviews.R
import kotlin.math.roundToInt

class GroupDividerItemDecoration2(context: Context, orientation: Int) :
RecyclerView.ItemDecoration() {
    val HORIZONTAL = LinearLayout.HORIZONTAL
    val VERTICAL = LinearLayout.VERTICAL

    private val TAG = "GroupDividerItem"
    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private var mEndDivider: Drawable? = null
    private var mStartDivider: Drawable? = null

    private var mOrientation: Int = 0

    private val mBounds = Rect()



    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mEndDivider = a.getDrawable(0)
        if (mEndDivider == null) {
            Log.w(
                TAG,
                "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setBottomDivider()"
            )
        }
        a.recycle()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        mOrientation = orientation
    }

    fun setBottomDivider(drawable: Drawable) {
        mEndDivider = drawable
    }

    fun setTopDivider(drawable: Drawable) {
        mStartDivider = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.layoutManager == null || mEndDivider == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

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
            if (!child.hasDivider()) continue
            parent.getDecoratedBoundsWithMargins(child, mBounds)


            val bottomForBottom = mBounds.bottom + child.translationY.roundToInt()
            val topForBottom = bottomForBottom - mEndDivider!!.intrinsicHeight
            mEndDivider!!.setBounds(left, topForBottom, right, bottomForBottom)
            mEndDivider!!.draw(canvas)

            val topForTop =mBounds.top + child.translationY.roundToInt()
            val bottomForTop=topForTop + mStartDivider!!.intrinsicHeight
            mStartDivider!!.setBounds(left, topForTop, right, bottomForTop)
            mStartDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (!child.hasDivider()) continue
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + child.translationX.roundToInt()
            val left = right - mEndDivider!!.intrinsicWidth
            mEndDivider!!.setBounds(left, top, right, bottom)
            mEndDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when {
            mEndDivider == null -> outRect.set(0, 0, 0, 0)
            !view.hasDivider() -> outRect.set(0, 0, 0, 0)
            mOrientation == VERTICAL -> outRect.set(0, mStartDivider!!.intrinsicHeight, 0, mEndDivider!!.intrinsicHeight)
            else -> outRect.set(mStartDivider!!.intrinsicHeight, 0, mEndDivider!!.intrinsicWidth, 0)
        }
    }

    private fun View.hasDivider():Boolean{
        return  this.getTag(R.string.groupDividerItemDecorationTag) == true
    }


}

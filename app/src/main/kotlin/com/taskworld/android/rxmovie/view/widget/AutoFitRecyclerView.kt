package com.taskworld.android.rxmovie.view.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public class AutoFitRecyclerView : RecyclerView {

    val manager: GridLayoutManager by Delegates.lazy { GridLayoutManager(getContext(), 1) }

    private var columnWidth = -1

    public constructor(context: Context) : super(context) {
        setUp(context)
    }

    public constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setUp(context, attrs)
    }

    public constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setUp(context, attrs)
    }

    fun setUp(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null) {
            val attrsArray = intArrayOf(android.R.attr.columnWidth)
            val ta = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = ta.getDimensionPixelSize(0, -1)
            ta.recycle()
        }

        setLayoutManager(manager)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = Math.max(1, getMeasuredWidth() / columnWidth)
            manager.setSpanCount(spanCount)
        }
    }
}
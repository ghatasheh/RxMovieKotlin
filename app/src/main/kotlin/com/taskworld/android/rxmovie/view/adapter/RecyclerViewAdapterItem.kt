package com.taskworld.android.rxmovie.view.adapter

import android.support.v7.widget.RecyclerView

/**
 * Created by Kittinun Vantasin on 7/18/15.
 */

interface RecyclerViewAdapterItem<T> {

    val viewType: Int

    fun bindViewHolder(viewHolder: T, position: Int)

    suppress("UNCHECKED_CAST")
    private fun bind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        bindViewHolder(viewHolder as T, position)
    }

}
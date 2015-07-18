package com.taskworld.android.rxmovie.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Kittinun Vantasin on 7/18/15.
 */

class ViewHolderFactory {

    val factory = hashMapOf<Int, Pair<Int, (View) -> RecyclerView.ViewHolder>>()

    fun getViewHolder(viewGroup: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val value = factory[viewType]

        val (resLayoutId, constructor) = value

        val view = LayoutInflater.from(viewGroup!!.getContext()).inflate(resLayoutId, viewGroup, false)

        return constructor(view)
    }

    fun registerViewHolder<T: RecyclerView.ViewHolder>(viewHolderId: Int, resLayoutId: Int, viewHolderConstructor: (View) -> T) {
        factory.put(viewHolderId, resLayoutId to viewHolderConstructor)
    }

}
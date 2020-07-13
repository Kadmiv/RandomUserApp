package com.kadmiv.random_user_app.base.adapters.recycler_adapter

import androidx.recyclerview.widget.DiffUtil
import com.kadmiv.random_user_app.base.interfaces.IModel


class BaseDiffUtilCallback<I : IModel>(val oldList: List<I>, val newList: List<I>) :
        DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].generateModelId() == newList[newItemPosition].generateModelId()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
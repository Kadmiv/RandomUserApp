package com.kadmiv.random_user_app.base.adapters.recycler_adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kadmiv.random_user_app.base.interfaces.IModel
import org.slf4j.LoggerFactory

open class BaseNormalAdapter<Item : IModel, Listener>(
    private var listener: Listener,
    private val layoutID: Int
) :
    RecyclerView.Adapter<BaseViewHolder<ViewDataBinding, Listener>>() {

    private val LOG = LoggerFactory.getLogger(BaseNormalAdapter::class.java)

    var mItems: ArrayList<Item> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return layoutID
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):
            BaseViewHolder<ViewDataBinding, Listener> {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding: ViewDataBinding?

        binding = DataBindingUtil.inflate(
            layoutInflater, viewType,
            viewGroup, false
        )

        return BaseViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        viewHolder: BaseViewHolder<ViewDataBinding, Listener>,
        position: Int
    ) {
        viewHolder.bind(mItems[position])
    }

    override fun getItemCount(): Int {
        if (mItems.isNotEmpty())
            return mItems.size
        return 0
    }

    fun onNewData(newData: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffUtilCallback(mItems, newData))
        this.mItems.clear()
        this.mItems.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

}
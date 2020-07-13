package com.kadmiv.random_user_app.base.adapters.recycler_adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding, L>(
        open val binding: B,
        open val listener: L
) :
        RecyclerView.ViewHolder(binding.root) {

    open fun <I> bind(model: I) {
        binding.setVariable(BR.model, model)
        binding.setVariable(BR.listener, listener)
        binding.executePendingBindings()
    }
}
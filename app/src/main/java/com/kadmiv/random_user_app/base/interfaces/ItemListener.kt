package com.kadmiv.random_user_app.base.interfaces


interface ItemListener<I> {

    fun onItemClicked(item: I)
    fun onItemLongClicked(item: I) {}
}
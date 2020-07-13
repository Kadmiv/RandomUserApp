package com.kadmiv.random_user_app.app.ui.page_list

import com.kadmiv.random_user_app.base.interfaces.IView
import com.kadmiv.random_user_app.repo.api.models.user.response.User

interface IPageList : IView {
    fun initRecyclerView(users: List<User>)
    fun showItemDetails(item: User)
    fun showLoadingView(visible: Boolean)
    fun hasConnection(): Boolean
    fun showErrorView(visible: Boolean)
    fun setErrorDescription(description: String)
    fun setConnectionErrorDescription()

}
package com.kadmiv.random_user_app.app.ui.page_item

import com.kadmiv.random_user_app.base.interfaces.IView
import com.kadmiv.random_user_app.repo.api.models.user.response.User

interface IPageItem : IView {
    fun callToPhone(number: String)
    fun createEmailFor(email: String)
    fun showAppSettings()
    fun setUserModel(selectedUser: User)
}
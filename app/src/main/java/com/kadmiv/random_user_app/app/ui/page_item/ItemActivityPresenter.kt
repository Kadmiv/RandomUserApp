package com.kadmiv.random_user_app.app.ui.page_item

import android.view.View
import com.kadmiv.random_user_app.base.abstractions.AbstractPresenter
import com.kadmiv.random_user_app.repo.DataManager
import com.kadmiv.random_user_app.repo.api.base.RequestResult
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ItemActivityPresenter : AbstractPresenter<IPageItem>(), KoinComponent {

    var LOG: Logger = LoggerFactory.getLogger(ItemActivityPresenter::class.java)

    private val mRepo: DataManager by inject()

    private lateinit var selectedUser: User

    override fun onAttach(view: IPageItem) {
        LOG.debug("")
        super.onAttach(view)
    }

    override fun onDetach() {
        LOG.debug("")
        super.onDetach()
    }

    fun onCallBtnClick() {
        mView?.callToPhone(selectedUser.phone)
    }

    fun onEmailBtnClick() {
        mView?.createEmailFor(selectedUser.email)
    }

    fun onAppSettingsBtnClicked() {
        mView?.showAppSettings()
    }

    fun getUserByID(userID: String?) {
        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            try {
                val userRequest = async { mRepo.getUserByID(userID) }

                val response = userRequest.await()
                selectedUser = response

                GlobalScope.launch(Dispatchers.Main) {
                    mView?.setUserModel(selectedUser)
                }
            } catch (ex: Exception) {
                LOG.error("", ex)
            }
        }
    }
}
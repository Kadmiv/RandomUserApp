package com.kadmiv.random_user_app.app.ui.page_list

import com.kadmiv.random_user_app.base.abstractions.AbstractPresenter
import com.kadmiv.random_user_app.base.interfaces.ItemListener
import com.kadmiv.random_user_app.repo.DataManager
import com.kadmiv.random_user_app.repo.api.base.RequestResult
import com.kadmiv.random_user_app.repo.api.mappers.ErrorMapper
import com.kadmiv.random_user_app.repo.api.mappers.UserMapper

import com.kadmiv.random_user_app.repo.api.models.user.response.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ListActivityPresenter :
    AbstractPresenter<IPageList>(),
    KoinComponent,
    ItemListener<User> {

    var LOG: Logger = LoggerFactory.getLogger(ListActivityPresenter::class.java)

    private val mRepo: DataManager by inject()
    private val userMapper: UserMapper by inject()
    private val errorMapper: ErrorMapper by inject()

    lateinit var users: ArrayList<User>

    override fun onAttach(view: IPageList) {
        LOG.debug("")
        super.onAttach(view)
    }

    override fun onStart() {
        if (!::users.isInitialized) {
            users = arrayListOf()
            loadUsers()
        } else {
            showUsers(users)
        }
    }

    override fun onDetach() {
        LOG.debug("")
        super.onDetach()
    }

    fun onTryAgainBtnClick() {
        mView?.showErrorView(false)
        loadUsers()
    }

    fun loadUsers() {
        LOG.debug("")

        if (mView?.hasConnection()!!) {
            showLoadingView(true)

            val job: Job = GlobalScope.launch(Dispatchers.IO) {

                try {
                    val userRequest = async { mRepo.getUsers() }

                    val response = userRequest.await()

                    if (response is RequestResult.Success) {
                        val loadedUsers = userMapper.toModels(response.data)

                        users.addAll(loadedUsers)
                        showUsers(users)
                    }

                    if (response is RequestResult.Error) {
                        val error = errorMapper.toModel(response.error)
                        mView?.setErrorDescription(error.error)
                        mView?.showErrorView(true)
                    }

                } catch (ex: Exception) {
                    LOG.error("", ex)
                }
            }
            job.invokeOnCompletion {
                showLoadingView(false)
            }
        } else {
            mView?.setConnectionErrorDescription()
            mView?.showErrorView(true)
        }
    }

    private fun showLoadingView(isVisible: Boolean) {
        GlobalScope.launch(Main) {
            mView?.showLoadingView(isVisible)
        }
    }

    private fun showUsers(users: List<User>) {
        GlobalScope.launch(Main) {
            mView?.initRecyclerView(users)
        }
    }


    override fun onItemClicked(item: User) {
        mView?.showItemDetails(item)
    }

}
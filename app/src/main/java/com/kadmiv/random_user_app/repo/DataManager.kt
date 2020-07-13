package com.kadmiv.random_user_app.repo

import com.kadmiv.random_user_app.repo.api.RestApiHelper
import com.kadmiv.random_user_app.repo.api.base.RequestResult
import com.kadmiv.random_user_app.repo.api.mappers.UserMapper
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.slf4j.LoggerFactory

class DataManager : IDataManager, KoinComponent {

    val LOG = LoggerFactory.getLogger(DataManager::class.java)

    val apiHelper: RestApiHelper by inject()

    private val userMapper: UserMapper by inject()

    private var usersMap: Map<String, User> = hashMapOf()

    override suspend fun getUsers(): RequestResult<String> {
        LOG.debug("")

        var response = apiHelper.getUsers();

        //FIXME - Эту часть кода приШлось добавить, чтобы сделать хоть какое-то подобие репозитория
        if (response is RequestResult.Success) {
            val loadedUsers = userMapper.toModels(response.data)
            usersMap = loadedUsers.map { it.firstName + it.lastName to it }.toMap()
        }

        return response
    }

    fun getUserByID(userID: String?): User {
        return usersMap[userID]!!
    }


}
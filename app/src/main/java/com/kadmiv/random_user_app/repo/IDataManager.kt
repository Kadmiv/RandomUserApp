package com.kadmiv.random_user_app.repo

import com.kadmiv.random_user_app.repo.api.base.RequestResult

interface IDataManager {

    suspend fun getUsers(): RequestResult<String>

}
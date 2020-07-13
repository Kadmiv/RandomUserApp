package com.kadmiv.random_user_app.di

import com.kadmiv.random_user_app.app.ui.page_item.ItemActivityPresenter
import com.kadmiv.random_user_app.app.ui.page_list.ListActivityPresenter
import com.kadmiv.random_user_app.repo.DataManager
import com.kadmiv.random_user_app.repo.api.RestApiHelper
import com.kadmiv.random_user_app.repo.api.mappers.ErrorMapper
import com.kadmiv.random_user_app.repo.api.mappers.UserMapper
import org.koin.dsl.module.module

val presenterModule = module {
    factory { ItemActivityPresenter() }
    single { ListActivityPresenter() }
}

val appModule = module {
//    factory { PreferencesHelper(androidApplication()) }
    single { DataManager() }
    factory { RestApiHelper("https://randomuser.me/") }
    factory { ErrorMapper() }
    factory { UserMapper() }
}
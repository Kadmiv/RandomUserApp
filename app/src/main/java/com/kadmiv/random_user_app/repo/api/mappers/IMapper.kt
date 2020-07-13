package com.kadmiv.random_user_app.repo.api.mappers

interface IMapper<Model> {

    fun toModel(data: String): Model
    fun toModels(data: String): List<Model>
}
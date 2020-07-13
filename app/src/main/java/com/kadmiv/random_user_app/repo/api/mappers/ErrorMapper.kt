package com.kadmiv.random_user_app.repo.api.mappers

import com.kadmiv.random_user_app.repo.api.base.ErrorAnswer
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception

class ErrorMapper :
    IMapper<ErrorAnswer> {
    private val LOG: Logger = LoggerFactory.getLogger(ErrorMapper::class.java)

    override fun toModel(data: String): ErrorAnswer {
        val item = ErrorAnswer()

        try {
            val json = JSONObject(data)

            item.error = json.getString("error")

        } catch (ex: Exception) {
            LOG.error("", ex)
        }

        return item
    }

    override fun toModels(data: String): List<ErrorAnswer> {
        return arrayListOf();
    }
}
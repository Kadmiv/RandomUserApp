package com.kadmiv.random_user_app.repo.api.mappers

import com.kadmiv.random_user_app.repo.api.models.user.response.User
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception

class UserMapper : IMapper<User> {
    private val LOG: Logger = LoggerFactory.getLogger(UserMapper::class.java)

    override fun toModel(data: String): User {
        val item = User()
        try {
            val json = JSONObject(data)

            val nameObj = json.optJSONObject("name")
            if (nameObj != null)
            {
                item.firstName = nameObj.optString("first")
                item.lastName = nameObj.optString("last")
            }

            val pictureObj = json.optJSONObject("picture")
            if (pictureObj != null)
            {
                item.medium = pictureObj.optString("medium")
                item.thumbnail = pictureObj.optString("thumbnail")
                item.large = pictureObj.optString("large")
            }

            val dobObj = json.optJSONObject("dob")
            if (dobObj != null)
            {
                item.age = dobObj.optInt("age",0)
            }

            item.email = json.optString("email")
            item.phone = json.optString("phone")
            item.cell = json.optString("cell")

        } catch (ex: Exception) {
            LOG.error("", ex)
        }

        return item
    }

    override fun toModels(data: String): List<User> {

        val items = arrayListOf<User>()
        try {

            val json = JSONObject(data)
            val jsonArray = json.getJSONArray("results")

            for (index in 0 until jsonArray.length()) {
                val jsonObj = jsonArray[index]
                val newItem = toModel(jsonObj.toString())
                items.add(newItem)
            }
        } catch (ex: Exception) {
            LOG.error("", ex)
        }

        return items
    }
}
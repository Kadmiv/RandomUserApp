package com.kadmiv.random_user_app.repo.api

import com.kadmiv.random_user_app.repo.api.base.EmptyBody
import com.kadmiv.random_user_app.repo.api.base.ErrorAnswer
import com.kadmiv.random_user_app.repo.api.base.RequestResult
import com.kadmiv.random_user_app.repo.api.base.RequestType
import com.kadmiv.random_user_app.repo.api.mappers.ErrorMapper
import com.kadmiv.random_user_app.repo.api.mappers.UserMapper
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.slf4j.LoggerFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


class RestApiHelper(baseApiUrl: String) : KoinComponent {

    private val LOG = LoggerFactory.getLogger(RestApiHelper::class.java);

    private lateinit var api: IRestApi


    private val TIME_OUT = 120L

    init {
        val clientBuilder = OkHttpClient.Builder()

        clientBuilder
            .connectTimeout(TimeUnit.SECONDS.toMillis(TIME_OUT), TimeUnit.SECONDS)
            .writeTimeout(TimeUnit.SECONDS.toMillis(TIME_OUT), TimeUnit.SECONDS)
            .readTimeout(TimeUnit.SECONDS.toMillis(TIME_OUT), TimeUnit.SECONDS)

        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        api = retrofit.create(IRestApi::class.java)
    }


    private fun doRequest(
        method: String,
        requestType: RequestType = RequestType.GET,
        headers: Map<String, String>,
        queryMap: Map<String, String>,
        dataItem: Any?
    ): Response<ResponseBody> {

        var dataItem = dataItem
        if (dataItem == null)
            dataItem = EmptyBody()

        val call = when (requestType) {
            RequestType.POST -> {
                api.sendUniversalPostRequest(method, headers, queryMap, dataItem)
            }
            RequestType.PUT -> {
                api.sendUniversalPutRequest(method, headers, queryMap, dataItem)
            }
            RequestType.GET -> {
                api.sendUniversalGetRequest(method, headers, queryMap)
            }
        }

        return call.execute();
    }




    suspend fun getUsers(): RequestResult<String> {

        val method = "api"

        val headers = hashMapOf<String, String>(
        )
        //https://randomuser.me/api/?page=2&results=20&inc=gender,name,picture
        val queryMap = hashMapOf<String, String>(
            Pair("page", "1"),
            Pair("results", "20")
        )

        val answer = doRequest(method, RequestType.GET, headers, queryMap, null)

        if (answer.isSuccessful) {
            return RequestResult.Success(answer.body()!!.string())
        } else {
            return RequestResult.Error(answer.errorBody()!!.string())
        }

    }


}
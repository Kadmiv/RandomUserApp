package com.kadmiv.random_user_app.repo.api

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface IRestApi {

    @POST("{path}")
    fun sendUniversalPostRequest(
        @Path("path") requestPath: String,
        @HeaderMap() headers: Map<String, String>,
        @QueryMap options: Map<String, String>,
        @Body dataItem: Any?
    ): Call<ResponseBody>

    @PUT("{path}")
    fun sendUniversalPutRequest(
        @Path("path") requestPath: String,
        @HeaderMap() headers: Map<String, String>,
        @QueryMap options: Map<String, String>,
        @Body dataItem: Any?
    ): Call<ResponseBody>

    @GET("{path}")
    fun sendUniversalGetRequest(
        @Path("path") requestPath: String,
        @HeaderMap() headers: Map<String, String>,
        @QueryMap options: Map<String, String>
    ): Call<ResponseBody>

    @GET("{path}")
    fun sendDeferredGetRequest(
        @Path("path") requestPath: String,
        @HeaderMap() headers: Map<String, String>,
        @QueryMap options: Map<String, String>
    ): Deferred<Response<ResponseBody>>
}
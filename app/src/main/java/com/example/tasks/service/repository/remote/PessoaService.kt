package com.example.tasks.service.repository.remote

import com.example.tasks.service.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PessoaService {

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("senha") senha: String
    ): Call<HeaderModel>

}
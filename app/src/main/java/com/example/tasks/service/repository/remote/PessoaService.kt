package com.example.tasks.service.repository.remote

import com.example.tasks.service.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PessoaService {

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") senha: String
    ): Call<HeaderModel>

    @POST("Authentication/Create")
    @FormUrlEncoded
    fun criarUsuario(
        @Field("name") nome: String,
        @Field("email") email: String,
        @Field("password") senha: String,
        @Field("receivernews") news: Boolean
    ): Call<HeaderModel>

}
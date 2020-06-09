package com.example.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var retrofit: Retrofit
        private val baseURL: String = "http://devmasterteam.com/CursoAndroidAPI/"

        private fun instanciaRetrofit(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> criarServico(serviceClass: Class<S>): S {
            return instanciaRetrofit()
                .create(serviceClass)
        }

    }

}
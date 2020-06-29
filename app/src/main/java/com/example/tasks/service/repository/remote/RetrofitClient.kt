package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var retrofit: Retrofit
        private val baseURL: String = "http://devmasterteam.com/CursoAndroidAPI/"

        private var tokenKey  = ""
        private var personKey  = ""

        private fun instanciaRetrofit(): Retrofit {

            val httpClient = OkHttpClient.Builder()

//            httpClient.addInterceptor { chain ->
//                val request = chain.request()
//                    .newBuilder()
//                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
//                    .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
//                    .build()
//                chain.proceed(request)
//            }

            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun addHeader(token: String, personKey: String){
            this.tokenKey = token
            this.personKey = personKey
        }

        fun <S> criarServico(serviceClass: Class<S>): S {
            return instanciaRetrofit()
                .create(serviceClass)
        }

    }

}
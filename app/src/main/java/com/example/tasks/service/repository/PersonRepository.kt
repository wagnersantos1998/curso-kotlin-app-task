package com.example.tasks.service.repository

import com.example.tasks.service.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.remote.PessoaService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PessoaRepository {

    private val mRemote = RetrofitClient.criarServico(PessoaService::class.java)

    fun login(email: String, senha: String, listener: APIListener) {

        val call: Call<HeaderModel> = mRemote.login(email, senha)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.falha(t.message.toString())
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validacao =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.falha(validacao)
                } else {
                    response.body()?.let { listener.sucesso(it) }
                }
            }

        })
    }

}
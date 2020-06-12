package com.example.tasks.service.repository

import com.example.tasks.service.HeaderModel
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.remote.PessoaService
import com.example.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PessoaRepository {

    private val mRemote = RetrofitClient.criarServico(PessoaService::class.java)

    fun login(email: String, senha: String, listener: APIListener) {

        val call: Call<HeaderModel> = mRemote.login(email, senha)
        call.enqueue(object : Callback<HeaderModel>{
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.falha(t.message.toString())
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                response.body()?.let { listener.sucesso(it) }
            }

        })
    }

}
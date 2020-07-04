package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.remote.RetrofitClient
import com.example.tasks.service.repository.remote.TarefaService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TarefaRepository(val context: Context) {

    private val mRemote = RetrofitClient.criarServico(TarefaService::class.java)

    fun salvarTarefa(tarefa: TarefaModel, listener: APIListener<Boolean>) {

        val call: Call<Boolean> = mRemote.criarTarefa(
            tarefa.prioridadeId,
            tarefa.descricao,
            tarefa.dataVencimento,
            tarefa.completo
        )

        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.falha(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS){
                val validacao = Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                listener.falha(validacao)
            } else {
                    response.body()?.let { listener.sucesso(it) }
                }
        }
    })

}
}
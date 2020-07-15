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

    private fun listar(call: Call<List<TarefaModel>>, listener: APIListener<List<TarefaModel>>) {
        call.enqueue(object : Callback<List<TarefaModel>> {
            override fun onFailure(call: Call<List<TarefaModel>>, t: Throwable) {
                listener.falha(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                call: Call<List<TarefaModel>>,
                response: Response<List<TarefaModel>>
            ) {
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

    fun listarTodasTarefa(listener: APIListener<List<TarefaModel>>) {
        val call: Call<List<TarefaModel>> = mRemote.listaTodasTarefas()
        listar(call, listener)
    }

    fun listarTarefaProximaSemana(listener: APIListener<List<TarefaModel>>) {
        val call: Call<List<TarefaModel>> = mRemote.listaProximaSemana()
        listar(call, listener)
    }

    fun listarTodasTarefaVencidas(listener: APIListener<List<TarefaModel>>) {
        val call: Call<List<TarefaModel>> = mRemote.listaTarefasVencidas()
        listar(call, listener)
    }

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

    fun atualizarTarefa(tarefa: TarefaModel, listener: APIListener<Boolean>) {

        val call: Call<Boolean> = mRemote.atualizarTarefa(
            tarefa.id,
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

    fun carregarTarefaId(id: Int, listener: APIListener<TarefaModel>) {

        val call: Call<TarefaModel> = mRemote.listaTarefaId(id)

        call.enqueue(object : Callback<TarefaModel> {
            override fun onFailure(call: Call<TarefaModel>, t: Throwable) {
                listener.falha(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<TarefaModel>, response: Response<TarefaModel>) {
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
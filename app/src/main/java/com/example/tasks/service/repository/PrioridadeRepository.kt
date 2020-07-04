package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.model.PrioridadeModel
import com.example.tasks.service.repository.local.TaskDatabase
import com.example.tasks.service.repository.remote.PrioridadeService
import com.example.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrioridadeRepository(val context: Context) {

    private val mRemote = RetrofitClient.criarServico(PrioridadeService::class.java)
    private val mPrioridadDatabase = TaskDatabase.getDatabase(context).DAO()

    fun resgatarPrioridades() {

        val call: Call<List<PrioridadeModel>> = mRemote.listaPrioridade()

        call.enqueue(object : Callback<List<PrioridadeModel>> {
            override fun onFailure(call: Call<List<PrioridadeModel>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<PrioridadeModel>>,
                response: Response<List<PrioridadeModel>>
            ) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    mPrioridadDatabase.excluirLista()
                    response.body()?.let { mPrioridadDatabase.salvarLista(it) }
                }
            }
        })
    }

    fun buscarPrioridades(): List<PrioridadeModel>{
        return mPrioridadDatabase.buscarPrioridade()
    }

}
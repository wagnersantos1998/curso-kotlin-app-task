package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.TarefaRepository
import java.util.ArrayList

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mTarefaRepository = TarefaRepository(application)

    private val mLista = MutableLiveData<List<TarefaModel>>()
    var lista: LiveData<List<TarefaModel>> = mLista

    private val mValidacao = MutableLiveData<Boolean>()
    var validacao: LiveData<Boolean> = mValidacao

    private val mTarefaCompleta = MutableLiveData<Boolean>()
    var tarefaCompleta: LiveData<Boolean> = mTarefaCompleta

    fun listaTodasTarefas() {

        mTarefaRepository.listarTodasTarefa(object : APIListener<List<TarefaModel>>{
            override fun sucesso(model: List<TarefaModel>) {
                mLista.value = model
            }

            override fun falha(mensagem: String) {
                mLista.value = arrayListOf()
            }

        })

    }

    fun deletarTarefa(id:Int){

        mTarefaRepository.deletarTarefa(id, object :APIListener<Boolean>{
            override fun sucesso(model: Boolean) {
                mValidacao.value = model
            }

            override fun falha(mensagem: String) {

            }

        })
    }

    fun atualizarTarefaCompleta(id:Int){

        mTarefaRepository.atualizarTarefaCompleta(id, object :APIListener<Boolean>{
            override fun sucesso(model: Boolean) {
                mTarefaCompleta.value = model
            }

            override fun falha(mensagem: String) {

            }

        })
    }

    fun atualizarTarefaNaoCompleta(id: Int){

        mTarefaRepository.atualizarTarefaNaoCompleta(id, object :APIListener<Boolean>{
            override fun sucesso(model: Boolean) {
                mTarefaCompleta.value = model
            }

            override fun falha(mensagem: String) {

            }

        })
    }

}
package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidacaoListener
import com.example.tasks.service.model.PrioridadeModel
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.PrioridadeRepository
import com.example.tasks.service.repository.TarefaRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mPrioridadeRepository = PrioridadeRepository(application)
    private val mTarefaRepository = TarefaRepository(application)

    private val mListaPrioridades = MutableLiveData<List<PrioridadeModel>>()
    var listaPrioridades: LiveData<List<PrioridadeModel>> = mListaPrioridades

    private val mAtualizacao = MutableLiveData<ValidacaoListener>()
    var atualizacao: LiveData<ValidacaoListener> = mAtualizacao

    private val mInsercao = MutableLiveData<ValidacaoListener>()
    var insercao: LiveData<ValidacaoListener> = mInsercao

    private val mTarefa = MutableLiveData<TarefaModel>()
    var tarefa: LiveData<TarefaModel> = mTarefa


    fun buscarPrioridades() {
        mListaPrioridades.value = mPrioridadeRepository.buscarPrioridades()
    }

    fun carregarDados(id: Int) {
        mTarefaRepository.carregarTarefaId(id, object : APIListener<TarefaModel> {
            override fun sucesso(model: TarefaModel) {
                mTarefa.value = model
            }

            override fun falha(mensagem: String) {

            }
        })
    }

    fun salvarTarefas(tarefa: TarefaModel) {
        if (tarefa.id == 0) {
            mTarefaRepository.salvarTarefa(tarefa, object : APIListener<Boolean> {
                override fun sucesso(model: Boolean) {
                    mInsercao.value = ValidacaoListener()
                }

                override fun falha(mensagem: String) {
                    mInsercao.value = ValidacaoListener(mensagem)
                }
            })
        } else {
            mTarefaRepository.atualizarTarefa(tarefa, object : APIListener<Boolean> {
                override fun sucesso(model: Boolean) {
                    mAtualizacao.value = ValidacaoListener()
                }

                override fun falha(mensagem: String) {
                    mAtualizacao.value = ValidacaoListener(mensagem)
                }

            })
        }
    }
}
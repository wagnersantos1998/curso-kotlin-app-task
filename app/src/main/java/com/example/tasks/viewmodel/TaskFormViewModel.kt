package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.model.PrioridadeModel
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.PrioridadeRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val prioridadeRepository = PrioridadeRepository(application)

    private val mListaPrioridades = MutableLiveData<List<PrioridadeModel>>()
    var listaPrioridades: LiveData<List<PrioridadeModel>> = mListaPrioridades

    fun buscarPrioridades() {
        mListaPrioridades.value = prioridadeRepository.buscarPrioridades()
    }

    fun salvarTarefas(tarefa: TarefaModel) {

    }

}
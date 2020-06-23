package com.example.tasks.service.repository.local

import androidx.room.*
import com.example.tasks.service.model.PrioridadeModel

@Dao
interface PrioridadeDAO {

    @Insert
    fun salvarLista(lista: List<PrioridadeModel>)

    @Query("DELETE FROM  prioridade")
    fun excluirLista()

}
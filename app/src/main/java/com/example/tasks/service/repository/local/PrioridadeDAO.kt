package com.example.tasks.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tasks.service.model.PrioridadeModel

@Dao
interface PrioridadeDAO {

    @Insert
    fun salvarLista(lista: List<PrioridadeModel>)

    @Query("SELECT descricao FROM  prioridade WHERE id = :id")
    fun buscarDescricao(id: Int): String

    @Query("SELECT * FROM  prioridade")
    fun buscarPrioridade(): List<PrioridadeModel>

    @Query("DELETE FROM  prioridade")
    fun excluirLista()

}
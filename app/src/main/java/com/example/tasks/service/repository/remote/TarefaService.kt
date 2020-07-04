package com.example.tasks.service.repository.remote

import com.example.tasks.service.model.TarefaModel
import retrofit2.Call
import retrofit2.http.*

interface TarefaService {

    @GET("Tasks")
    fun listaTodasTarefas(): Call<List<TarefaModel>>

    @GET("Tasks/Next7Days")
    fun listaProximaSemana(): Call<List<TarefaModel>>

    @GET("Tasks/Overdue")
    fun listaTarefasVencidas(): Call<List<TarefaModel>>

    @GET("Tasks/{id}")
    fun listaTarefaId(@Path("id", encoded = true) id: Int): Call<List<TarefaModel>>

    @POST("Task")
    @FormUrlEncoded
    fun criarTarefa(

        @Field("PriorityId") prioridadeId: Int,

        @Field("Description") descricao: String,

        @Field("DueDate") dataVencimento: String,

        @Field("Complete") completo: Boolean

    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task", hasBody = true)
    @FormUrlEncoded
    fun atualizarTarefa(

        @Field("id") id: Int,

        @Field("priorityId") prioridadeId: Int,

        @Field("descricao") descricao: String,

        @Field("dueDate") dataVencimento: String,

        @Field("complete") completo: Boolean

    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task/Complete", hasBody = true)
    @FormUrlEncoded
    fun atualizarTarefaCompletada(

        @Field("id") id: Int

    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task/Undo", hasBody = true)
    @FormUrlEncoded
    fun atualizarTarefaNaoCompletada(

        @Field("id") id: Int

    ): Call<Boolean>

    @HTTP(method = "DELETE", path = "Task", hasBody = true)
    @FormUrlEncoded
    fun deletarTarefa(

        @Field("id") id: Int

    ): Call<Boolean>

}
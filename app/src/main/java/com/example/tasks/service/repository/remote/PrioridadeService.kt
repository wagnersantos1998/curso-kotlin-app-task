package com.example.tasks.service.repository.remote

import com.example.tasks.service.model.ModeloPrioridade
import retrofit2.Call
import retrofit2.http.GET

interface PrioridadeService {

 @GET("Priority")
 fun listaPrioridade(): Call<List<ModeloPrioridade>>

}
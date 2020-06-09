package com.example.tasks.service.repository

import com.example.tasks.service.repository.remote.PessoaService
import com.example.tasks.service.repository.remote.RetrofitClient

class PersonRepository {

    val remote = RetrofitClient.criarServico(PessoaService::class.java)

    fun login(email: String, senha: String) {
        remote.login(email, senha)
    }

}
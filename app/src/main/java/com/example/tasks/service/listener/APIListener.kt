package com.example.tasks.service.listener

interface APIListener<t> {

    fun sucesso(model: t)
    fun falha(mensagem: String)

}
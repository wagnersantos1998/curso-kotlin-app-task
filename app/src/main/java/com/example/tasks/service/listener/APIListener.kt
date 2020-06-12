package com.example.tasks.service.listener

import com.example.tasks.service.HeaderModel

interface APIListener {

    fun sucesso(model: HeaderModel)
    fun falha(mensagem: String)

}
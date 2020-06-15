package com.example.tasks.service.listener

class ValidacaoListener(mensagem: String = "") {

    private var status: Boolean = true
    private var mMensagem: String = ""

    init {
        if (mensagem != "") {
            status = false
            mMensagem = mensagem
        }
    }

    fun sucesso() = status
    fun falha() = mMensagem

}
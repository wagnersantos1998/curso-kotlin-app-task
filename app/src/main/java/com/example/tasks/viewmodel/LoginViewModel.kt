package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.repository.PessoaRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPessoaRepository = PessoaRepository()

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, senha: String) {

        mPessoaRepository.login(email, senha, object : APIListener {

            override fun falha(mensagem: String) {
                val retorno = ""
            }

            override fun sucesso(model: HeaderModel) {
                val retorno = ""
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}
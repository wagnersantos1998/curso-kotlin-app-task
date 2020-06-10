package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasks.service.repository.PessoaRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPessoaRepository = PessoaRepository()

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, senha: String) {

        mPessoaRepository.login(email, senha)

    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}
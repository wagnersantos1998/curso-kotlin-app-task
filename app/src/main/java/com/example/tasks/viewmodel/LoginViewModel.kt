package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidacaoListener
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.repository.PessoaRepository
import com.example.tasks.service.repository.PrioridadeRepository
import com.example.tasks.service.repository.local.SecurityPreferences
import com.example.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPessoaRepository = PessoaRepository(application)
    private val mPrioridadeRepository = PrioridadeRepository(application)
    private val mSharedPreference = SecurityPreferences(application)

    private val mLogin = MutableLiveData<ValidacaoListener>()
    var login: LiveData<ValidacaoListener> = mLogin

    private val mLogado = MutableLiveData<Boolean>()
    var logado: LiveData<Boolean> = mLogado

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, senha: String) {

        mPessoaRepository.login(email, senha, object : APIListener<HeaderModel> {

            override fun sucesso(model: HeaderModel) {

                mSharedPreference.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreference.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreference.store(TaskConstants.SHARED.PERSON_NAME, model.nome)

                RetrofitClient.addHeader(model.token, model.personKey)

                mLogin.value = ValidacaoListener()
            }

            override fun falha(mensagem: String) {
                mLogin.value = ValidacaoListener(mensagem)
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        val token = mSharedPreference.get(TaskConstants.SHARED.TOKEN_KEY)
        val personKey = mSharedPreference.get(TaskConstants.SHARED.PERSON_KEY)

        RetrofitClient.addHeader(token, personKey)

        val logado = (token != "" && personKey != "")

        if (!logado){
            mPrioridadeRepository.resgatarPrioridades()
        }

        mLogado.value = logado

    }

}
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
import com.example.tasks.service.repository.local.SecurityPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPessoaRepository = PessoaRepository(application)
    private val mSharedPreference = SecurityPreferences(application)

    private val mCriar = MutableLiveData<ValidacaoListener>()
    var criar: LiveData<ValidacaoListener> = mCriar

    fun create(nome: String, email: String, senha: String) {
        mPessoaRepository.criarUsuario(nome, email, senha, object : APIListener <HeaderModel>{
            override fun sucesso(model: HeaderModel) {
                mSharedPreference.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreference.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreference.store(TaskConstants.SHARED.PERSON_NAME, model.nome)

                mCriar.value = ValidacaoListener()
            }

            override fun falha(mensagem: String) {
                mCriar.value = ValidacaoListener(mensagem)
            }
        })
    }

}
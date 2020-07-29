package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.repository.local.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mSharedPreference = SecurityPreferences(application)

    private val mNome = MutableLiveData<String>()
    var nome: LiveData<String> = mNome

    private val mLogout = MutableLiveData<Boolean>()
    var logout: LiveData<Boolean> = mLogout

    fun buscarNomeUsuario() {
        mNome.value = mSharedPreference.get(TaskConstants.SHARED.PERSON_NAME)
    }

    fun logout() {
        mSharedPreference.remove(TaskConstants.HEADER.PERSON_KEY)
        mSharedPreference.remove(TaskConstants.HEADER.TOKEN_KEY)
        mSharedPreference.remove(TaskConstants.SHARED.PERSON_NAME)

        mLogout.value = true
    }

}
package com.example.tasks.funcoes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build

class VerificarConexao : BroadcastReceiver() {
    enum class TipoConexao {
        TIPO_NAO_CONECTADO, TIPO_WIFI, TIPO_MOBILE
    }

    private var tipoConexaoATUAL = TipoConexao.TIPO_NAO_CONECTADO //cache
    private var inicializou = false
    private fun getStatusConexao(context: Context): TipoConexao {
        synchronized(tipoConexaoATUAL) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            tipoConexaoATUAL = TipoConexao.TIPO_NAO_CONECTADO
            val activeNetwork = cm?.activeNetworkInfo
            if (null != activeNetwork) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    tipoConexaoATUAL = TipoConexao.TIPO_WIFI
                }
                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    tipoConexaoATUAL = TipoConexao.TIPO_MOBILE
                }
            }
            return tipoConexaoATUAL
        }
    }

    fun getTipoConexaoAtual(context: Context): TipoConexao {
        if (!inicializou) {
            inicializou = true
            return getStatusConexao(context)
        }
        return tipoConexaoATUAL
    }

    interface IOnMudarEstadoConexao {
        fun onMudar(tipoConexao: TipoConexao?)
    }

    private val onMudarEstadoConexoesListeners: ArrayList<IOnMudarEstadoConexao> = ArrayList()
    fun addOnMudarEstadoConexao(t: IOnMudarEstadoConexao) {
        onMudarEstadoConexoesListeners.add(t)
    }

    fun removeOnMudarEstadoConexao(t: IOnMudarEstadoConexao?) {
        onMudarEstadoConexoesListeners.remove(t)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val tipo = getStatusConexao(context)
        for (o in onMudarEstadoConexoesListeners) {
            o.onMudar(tipo)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                for (net in connectivityManager.allNetworks) {
                    val networkInfo = connectivityManager.getNetworkInfo(net)
                    if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                        connectivityManager.bindProcessToNetwork(net)
                        break
                    }
                }
            }
        }
    }
}
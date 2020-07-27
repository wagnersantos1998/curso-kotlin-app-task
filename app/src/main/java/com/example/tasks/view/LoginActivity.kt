package com.example.tasks.view

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.funcoes.VerificarConexao
import com.example.tasks.funcoes.mensagensSnack
import com.example.tasks.funcoes.pintarSnack
import com.example.tasks.funcoes.toast
import com.example.tasks.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var aux: Int = 0

        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        val conexaoInternet = VerificarConexao()
        registerReceiver(conexaoInternet, intentFilter)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Inicializa eventos
        setListeners();
        observe()

        // Verifica se usuário está logado
        verifyLoggedUser()

        conexaoInternet.addOnMudarEstadoConexao(
            object : VerificarConexao.IOnMudarEstadoConexao {
                override fun onMudar(tipoConexao: VerificarConexao.TipoConexao?) {
                    if (tipoConexao === VerificarConexao.TipoConexao.TIPO_NAO_CONECTADO) {
                        pintarSnack(mensagensSnack("Sem Conexão!", layoutLogin, Snackbar.LENGTH_INDEFINITE), Color.WHITE, Color.RED)
                        progressBarLogin.visibility = View.VISIBLE
                        aux = 1
                    } else if (aux === 1) {
                        pintarSnack(mensagensSnack("Conectado!", layoutLogin, Snackbar.LENGTH_SHORT), Color.WHITE, Color.GREEN)
                        progressBarLogin.visibility = View.INVISIBLE
                        aux = 0
                    }
                }
            }
        )

    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_login) {
            handleLogin()
        } else if (v.id == R.id.text_register) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        button_login.setOnClickListener(this)
        text_register.setOnClickListener(this)
    }

    /**
     * Verifica se usuário está logado
     */
    private fun verifyLoggedUser() {
        mViewModel.verifyLoggedUser()
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
        mViewModel.login.observe(this, Observer {
            if (it.sucesso()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val mensagem = it.falha()
                toast(this, mensagem, Toast.LENGTH_LONG)
            }
        })

        mViewModel.logado.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    /**
     * Autentica usuário
     */
    private fun handleLogin() {
        val email = edit_email.text.toString()
        val senha = edit_password.text.toString()

        mViewModel.doLogin(email, senha)
    }

}

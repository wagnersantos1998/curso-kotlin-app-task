package com.example.tasks.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Inicializa eventos
        listeners()
        observe()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save) {

            val nome = edit_name.text.toString()
            val email = edit_email.text.toString()
            val senha = edit_password.text.toString()

            mViewModel.create(nome, email, senha)
        }
    }

    private fun observe() {
        mViewModel.criar.observe(this, Observer {
            if (it.sucesso()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val mensagem = it.falha()
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun listeners() {
        button_save.setOnClickListener(this)
    }
}

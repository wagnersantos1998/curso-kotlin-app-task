package com.example.tasks.funcoes

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun toast(context: Context, mensagem: String, duracao: Int){
    Toast.makeText(context, mensagem, duracao).show()
}

fun mensagensSnack(mensagem: String, view: View, duracao: Int): Snackbar {
    var snackbar = Snackbar.make(view, mensagem, duracao)
    return snackbar
}

fun pintarSnack(snackbar: Snackbar, corTexto: Int, corFundo: Int) {
    snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(corTexto)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
    } else {
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).gravity =
            Gravity.CENTER_HORIZONTAL
    }
    snackbar.view.setBackgroundColor(corFundo)
    snackbar.show()
}
package com.example.tasks.view

import android.app.DatePickerDialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.funcoes.VerificarConexao
import com.example.tasks.funcoes.mensagensSnack
import com.example.tasks.funcoes.pintarSnack
import com.example.tasks.funcoes.toast
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.viewmodel.TaskFormViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*


class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var mViewModel: TaskFormViewModel
    private val mDataFormatada = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    var tarefaId = 0
    val mListaPrioridadeId: MutableList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        var aux: Int = 0

        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        val conexaoInternet = VerificarConexao()
        registerReceiver(conexaoInternet, intentFilter)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        // Inicializa eventos
        listeners()
        observe()
        listarPrioridades()

        carregarDadosTarefa()

        conexaoInternet.addOnMudarEstadoConexao(
            object : VerificarConexao.IOnMudarEstadoConexao {
                override fun onMudar(tipoConexao: VerificarConexao.TipoConexao?) {
                    if (tipoConexao === VerificarConexao.TipoConexao.TIPO_NAO_CONECTADO) {
                        pintarSnack(mensagensSnack("Sem Conexão!", layoutFormTarefa, Snackbar.LENGTH_INDEFINITE), Color.WHITE, Color.RED)
                        aux = 1
                    } else if (aux === 1) {
                        pintarSnack(mensagensSnack("Conectado!", layoutFormTarefa, Snackbar.LENGTH_SHORT), Color.WHITE, Color.GREEN)
                        aux = 0
                    }
                }
            }
        )

    }

    private fun carregarDadosTarefa() {
        val bundle = intent.extras
        if (bundle != null) {
            button_save.setText("Atualizar tarefa")
            tarefaId = bundle.getInt(TaskConstants.BUNDLE.TASKID)
            mViewModel.carregarDados(tarefaId)
        } else{
            button_save.setText(R.string.adicionar_tarefa)
        }
    }

    private fun listarPrioridades() {
        mViewModel.buscarPrioridades()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save) {

            eventoSalvar()

        } else if (id == R.id.button_date) {
            showDatePicker()
        }
    }

    private fun eventoSalvar() {
        val tarefa = TarefaModel().apply {
            this.id = tarefaId
            this.descricao = edit_description.text.toString()
            this.completo = check_complete.isChecked
            this.dataVencimento = button_date.text.toString()
            this.prioridadeId = mListaPrioridadeId[spinner_priority.selectedItemPosition]
        }
        mViewModel.salvarTarefas(tarefa)
    }

    private fun showDatePicker() {

        val calendario = Calendar.getInstance()

        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH)
        val ano = calendario.get(Calendar.YEAR)

        DatePickerDialog(this, this, ano, mes, dia).show()
    }

    private fun observe() {
        mViewModel.listaPrioridades.observe(this, androidx.lifecycle.Observer {

            val lista: MutableList<String> = arrayListOf()

            for (item in it) {
                lista.add(item.descricao)
                mListaPrioridadeId.add(item.id)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista)
            spinner_priority.adapter = adapter

        })
        mViewModel.insercao.observe(this, androidx.lifecycle.Observer {
            if (it.sucesso()) {
                toast(this, "Tarefa criada com sucesso", Toast.LENGTH_LONG)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                toast(this, it.falha(), Toast.LENGTH_LONG)
            }
        })
        mViewModel.atualizacao.observe(this, androidx.lifecycle.Observer {
            if (it.sucesso()) {
                toast(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_LONG)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                toast(this, it.falha(), Toast.LENGTH_LONG)
            }
        })
        mViewModel.tarefa.observe(this, androidx.lifecycle.Observer {

            edit_description.setText(it.descricao)
            check_complete.isChecked = it.completo

            spinner_priority.setSelection(pegarIndex(it.prioridadeId))

            val dataFormatada =
                SimpleDateFormat("yyyy-MM-dd").parse(it.dataVencimento)
            button_date.text = mDataFormatada.format(dataFormatada)

        })
    }

    private fun pegarIndex(prioridadeId: Int): Int {
        var index = 0
        for (i in 0 until mListaPrioridadeId.count())
            if (mListaPrioridadeId[i] == prioridadeId) {
                index = i
                break
            }
        return index
    }

    private fun listeners() {
        button_date.setOnClickListener(this)
        button_save.setOnClickListener(this)
    }

    override fun onDateSet(view: DatePicker?, ano: Int, mes: Int, diaMes: Int) {

        val calendario = Calendar.getInstance()
        calendario.set(ano, mes, diaMes)

        val dataFormatada = mDataFormatada.format(calendario.time)
        button_date.text = dataFormatada
    }

}

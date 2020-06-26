package com.example.tasks.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.viewmodel.TaskFormViewModel
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var mViewModel: TaskFormViewModel
    private val mDataFormatada = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val mListaPrioridadeId: MutableList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        // Inicializa eventos
        listeners()
        observe()
        listarPrioridades()

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

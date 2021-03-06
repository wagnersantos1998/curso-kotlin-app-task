package com.example.tasks.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.funcoes.toast
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.TaskListener
import com.example.tasks.view.adapter.TaskAdapter
import com.example.tasks.viewmodel.AllTasksViewModel

class AllTasksFragment : Fragment() {

    private lateinit var mViewModel: AllTasksViewModel
    private lateinit var mListener: TaskListener

    private val mAdapter = TaskAdapter()

    private var mTarefaFiltro = 0

    var aux: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        mViewModel = ViewModelProvider(this).get(AllTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_tasks, container, false)

        mTarefaFiltro = arguments!!.getInt(TaskConstants.BUNDLE.TAREFAFILTRO, 0)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_tasks)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        // Eventos disparados ao clicar nas linhas da RecyclerView
        mListener = object : TaskListener {
            override fun onListClick(id: Int) {
                val intent = Intent(context, TaskFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                mViewModel.deletarTarefa(id)
            }

            override fun onCompleteClick(id: Int) {
                mViewModel.atualizarTarefaCompleta(id)
            }

            override fun onUndoClick(id: Int) {
                mViewModel.atualizarTarefaNaoCompleta(id)
            }
        }

        // Cria os observadores
        observe()

        // Retorna view
        return root

    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.listaTodasTarefas(mTarefaFiltro)
    }

    private fun observe() {
        mViewModel.validacao.observe(viewLifecycleOwner, Observer {
            if (it) {
                var mensagem: String = "Tarefa removida com sucesso!"
                val context = requireContext()
                toast(context, mensagem, Toast.LENGTH_LONG)
                mViewModel.listaTodasTarefas(mTarefaFiltro)
            }
        })
        mViewModel.tarefaCompleta.observe(viewLifecycleOwner, Observer {
            if (it) {
                mViewModel.listaTodasTarefas(mTarefaFiltro)
            }
        })

        mViewModel.lista.observe(viewLifecycleOwner, Observer {
            if (it.count() >= 0) {
                mAdapter.atualizarLista(it)
            }
        })
    }

}

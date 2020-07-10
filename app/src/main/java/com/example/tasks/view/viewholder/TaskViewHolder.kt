package com.example.tasks.view.viewholder

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.service.listener.TaskListener
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.PrioridadeRepository
import java.text.SimpleDateFormat
import java.util.*

class TaskViewHolder(itemView: View, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemView) {

    private val mData = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    private val repository = PrioridadeRepository(itemView.context)

    private var mTextDescription: TextView = itemView.findViewById(R.id.text_description)
    private var mTextPriority: TextView = itemView.findViewById(R.id.text_priority)
    private var mTextDueDate: TextView = itemView.findViewById(R.id.text_due_date)
    private var mImageTask: ImageView = itemView.findViewById(R.id.image_task)

    /**
     * Atribui valores aos elementos de interface e também eventos
     */
    fun bindData(tarefa: TarefaModel) {

        this.mTextDescription.text = tarefa.descricao


        this.mTextPriority.text = repository.buscarDescricao(tarefa.prioridadeId)

        val dataFormatada =
            SimpleDateFormat("yyyy-MM-dd").parse(tarefa.dataVencimento)
        this.mTextDueDate.text = mData.format(dataFormatada)

        if (tarefa.completo) {
            mTextDescription.setTextColor(Color.GRAY)
            mImageTask.setImageResource(R.drawable.ic_done)
        } else {
            mTextDescription.setTextColor(Color.BLACK)
            mImageTask.setImageResource(R.drawable.ic_todo)
        }

        // Eventos
        mTextDescription.setOnClickListener { listener.onListClick(tarefa.id) }
        mImageTask.setOnClickListener {
            if (tarefa.completo) {
                listener.onUndoClick(tarefa.id)
            } else {
                listener.onCompleteClick(tarefa.id)
            }
        }

        mTextDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    listener.onDeleteClick(tarefa.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
            true
        }

    }

}
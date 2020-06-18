package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class TarefaModel {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("priorityId")
    var prioridadeId: Int = 0

    @SerializedName("descricao")
    var descricao: String = ""

    @SerializedName("dueDate")
    var dataVencimento: String = ""

    @SerializedName("complete")
    var completo: Boolean = false
}
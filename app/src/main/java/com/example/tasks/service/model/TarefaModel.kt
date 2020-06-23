package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class TarefaModel {

    @SerializedName("Id")
    var id: Int = 0

    @SerializedName("PriorityId")
    var prioridadeId: Int = 0

    @SerializedName("Description")
    var descricao: String = ""

    @SerializedName("DueDate")
    var dataVencimento: String = ""

    @SerializedName("Complete")
    var completo: Boolean = false
}
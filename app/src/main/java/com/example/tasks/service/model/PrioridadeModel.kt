package com.example.tasks.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "prioridade")
class PrioridadeModel {

    @SerializedName("Id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0

    @SerializedName("Description")
    @ColumnInfo(name = "descricao")
    var descricao: String = ""

}
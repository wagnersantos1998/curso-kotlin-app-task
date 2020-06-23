package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class HeaderModel {

    @SerializedName("Token")
    var token: String = ""

    @SerializedName("PersonKey")
    var personKey: String = ""

    @SerializedName("Name")
    var nome: String = ""

}

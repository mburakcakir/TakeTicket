package com.mburakcakir.taketicket.data.network.model

import com.google.gson.annotations.SerializedName

data class InfoModel(
    @SerializedName("account")
    val account: String,
    @SerializedName("site_url")
    val siteUrl: String,
    @SerializedName("image_url")
    val imageUrl: String
)
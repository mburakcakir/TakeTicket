package com.mburakcakir.taketicket.data.remote.model.event

data class Genre(
    val document_source: DocumentSource,
    val id: Int,
    val image: String,
    val images: İmages,
    val name: String,
    val primary: Boolean,
    val slug: String
)
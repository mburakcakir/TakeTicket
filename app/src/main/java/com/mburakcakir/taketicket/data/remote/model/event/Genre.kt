package com.mburakcakir.taketicket.data.remote.model.event

data class Genre(
    val document_source: DocumentSource,
    val id: Int,
    val image: String,
    val images: Images,
    val name: String,
    val primary: Boolean,
    val slug: String
)
package com.mburakcakir.taketicket.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_event")
data class EventModel(
    val title: String,
    val subTitle: String,
    val category: String,
    val capacity: Int,
    val url: String,
    val time: String,
    val price: String
) {
    @PrimaryKey(autoGenerate = true)
    var eventID: Int = 0
}
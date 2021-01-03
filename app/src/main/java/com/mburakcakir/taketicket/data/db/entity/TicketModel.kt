package com.mburakcakir.taketicket.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_ticket")
data class TicketModel(
    val name: String,
    val email: String,
    val boughtTime: String,
    val eventID: Int
){
    @PrimaryKey(autoGenerate = true)
    var ticketID : Int = 0
}
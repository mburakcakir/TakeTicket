package com.mburakcakir.taketicket.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_ticket")
data class TicketModel(
    val name : String,
    val email : String,
    val title : String,
    val price : String,
    val time : String,
    val category: String,
    val boughtTime : String,
    val url : String
){
    @PrimaryKey(autoGenerate = true)
    var ticketID : Int = 0
}
package com.mburakcakir.taketicket.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "table_ticket")
data class TicketModel(
    val username: String,
    val email: String,
    val boughtTime: String,
    val eventID: Int,
    val ticketType: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var ticketID: Int = 0
}
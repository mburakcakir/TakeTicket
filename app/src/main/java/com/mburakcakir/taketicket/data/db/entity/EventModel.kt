package com.mburakcakir.taketicket.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_event")
data class EventModel(
    val eventID: Int,
    val title: String,
    val subTitle: String,
    val category: String,
    val capacity: Int,
    val url: String,
    val time: String,
    val price: String,
    val type: String = "Turkish"
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0
}
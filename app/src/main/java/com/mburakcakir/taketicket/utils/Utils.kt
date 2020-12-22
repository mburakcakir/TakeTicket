package com.mburakcakir.taketicket.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): String {
    val currentTime: Date = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("hh.mm")
    return dateFormat.format(currentTime)
}
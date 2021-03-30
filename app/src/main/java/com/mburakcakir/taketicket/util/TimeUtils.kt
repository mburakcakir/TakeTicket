package com.mburakcakir.taketicket.util

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): String {
    val currentTime: Date = Calendar.getInstance().time
    return SimpleDateFormat("hh.mm").format(currentTime)

}
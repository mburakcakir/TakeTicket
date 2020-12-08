package com.mburakcakir.taketicket.data.repository.info

import com.mburakcakir.taketicket.data.network.model.InfoModel

interface InfoRepository {
    suspend fun getSchedule(): List<InfoModel>
}
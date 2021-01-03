package com.mburakcakir.taketicket.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mburakcakir.taketicket.data.db.entity.EventModel

@Dao
interface EventDao {
    @Query("SELECT * FROM table_event")
    fun getAllEvents(): List<EventModel>

    @Insert
    suspend fun insertEvent(eventModel: EventModel)

    @Query("DELETE FROM table_event")
    fun deleteAllEvents()

    @Query("SELECT * FROM table_event WHERE eventID =:ID")
    fun getEventById(ID: Int): EventModel

}
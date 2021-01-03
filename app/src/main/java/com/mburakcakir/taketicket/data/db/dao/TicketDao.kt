package com.mburakcakir.taketicket.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mburakcakir.taketicket.data.db.entity.TicketModel

@Dao
interface TicketDao{
    @Query("SELECT * FROM table_ticket WHERE name =:username")
    fun getAllTickets(username: String): List<TicketModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticketModel: TicketModel)

    @Query("SELECT COUNT(*) FROM table_ticket where ticketID =:ticketID")
    fun checkIfTicketExists(ticketID: Int): Int

    @Query("DELETE FROM table_ticket WHERE ticketID=:id")
    suspend fun deleteTicket(id: Int)
}
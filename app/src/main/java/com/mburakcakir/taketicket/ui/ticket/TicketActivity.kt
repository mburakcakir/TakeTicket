package com.mburakcakir.taketicket.ui.ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.vbtinternshipschedule.ui.adapter.TicketAdapter
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    var backPressedTime : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        toolbarTicket.setTitle(resources.getString(R.string.tickets))
        setSupportActionBar(toolbarTicket)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        rvTicket.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Adapter çağırıldığı yer
        rvTicket.adapter = TicketAdapter(ticketViewModel.getAllTickets(ticketViewModel.getUsername()!!))
    }
}
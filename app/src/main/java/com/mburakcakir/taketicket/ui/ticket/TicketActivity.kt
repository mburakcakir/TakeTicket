package com.mburakcakir.taketicket.ui.ticket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        init()
    }

    fun init() {
        toolbarTicket.title = getString(R.string.tickets)
        setSupportActionBar(toolbarTicket)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        ticketViewModel.getAllTickets()

        rvTicket.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTicket.adapter = TicketAdapter(ticketViewModel) {
            ticketViewModel.apply {
                deleteTicket(it.ticketID)
                getAllTickets()
            }
        }

        ticketViewModel.allTickets.observe(this, {
            (rvTicket.adapter as TicketAdapter).submitList(it)
        })
    }
}
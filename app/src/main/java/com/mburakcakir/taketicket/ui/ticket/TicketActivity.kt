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
        rvTicket.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTicket.adapter = TicketAdapter()

        // Adapter çağırıldığı yer
        // Observer Pattern, allTickets değişkeni her değiştiğinde observe metodu tetiklenecek ve adapter güncellenecek.
        // Böylece veri değişimini elle kontrol etmemize gerek kalmayacak. Hem güncel veriyi alacağız hem de set işlemlerini yapacağız.
        ticketViewModel.allTickets.observe(this, {
            (rvTicket.adapter as TicketAdapter).submitList(it)
        })

    }
}
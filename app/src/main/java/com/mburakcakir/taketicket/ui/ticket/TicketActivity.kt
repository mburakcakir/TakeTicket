package com.mburakcakir.taketicket.ui.ticket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.ActivityTicketBinding

class TicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var binding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        binding.toolbarTicket.title = getString(R.string.tickets)
        setSupportActionBar(binding.toolbarTicket)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        binding.rvTicket.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvTicket.adapter = TicketAdapter(ticketViewModel.allEvents.value!!) {
            ticketViewModel.apply {
                deleteTicket(it.ticketID)
            }
        }
        ticketViewModel.allTickets.observe(this, {
            (binding.rvTicket.adapter as TicketAdapter).submitList(it)
        })
    }
}
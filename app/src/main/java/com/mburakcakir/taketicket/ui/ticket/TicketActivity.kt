package com.mburakcakir.taketicket.ui.ticket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.ActivityTicketBinding
import com.mburakcakir.taketicket.utils.extToast

class TicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var binding: ActivityTicketBinding
    var message: String = ""
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
            ticketViewModel.deleteTicket(it.ticketID)
        }

        ticketViewModel.allTickets.observe(this, {
            (binding.rvTicket.adapter as TicketAdapter).submitList(it)
        })

        ticketViewModel.result.observe(this, {
            when {
                it.success != null -> message = it.success
                it.error != null -> message = it.error
                it.loading != null -> message = it.loading
                it.warning != null -> message = it.warning
            }
            this@TicketActivity extToast message
        })
    }
}
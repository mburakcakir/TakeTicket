package com.mburakcakir.taketicket.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.entry.login.LoginActivity
import com.mburakcakir.taketicket.ui.event.DetailDialog
import com.mburakcakir.taketicket.ui.event.EventAdapter.EventAdapter
import com.mburakcakir.taketicket.ui.info.InfoActivity
import com.mburakcakir.taketicket.ui.ticket.TicketActivity
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
import kotlinx.android.synthetic.main.activity_event.*


class EventActivity : AppCompatActivity() {
    private lateinit var eventViewModel: EventViewModel
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        init()
    }

    fun init() {

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        toolbar.title = "Hoşgeldin ${eventViewModel.getUsername()}"
        setSupportActionBar(toolbar)
        rvEvent.adapter = EventAdapter {
            DetailDialog(
                ticketModel = it,
                onClickApproved = {
                    this extToast getString(R.string.success_ticket)
                    this extOpenActivity TicketActivity::class.java
                },
                onClickDenied = {
                    this extToast getString(R.string.warn_ticket)
                }).show(supportFragmentManager, "DetailDialog")
        }
        rvEvent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        eventViewModel.getAllEvents()
        eventViewModel.allEvents.observe(this, { allEvents ->
            allEvents?.let {
                (rvEvent.adapter as EventAdapter).submitList(allEvents)
                Log.d("tag2", allEvents.toString())
            }
        })
    }

    override fun onBackPressed() {
        if (backPressedTime + 5000 > System.currentTimeMillis())
            endSession()
        else
            Toast.makeText(baseContext, getString(R.string.exit_app), Toast.LENGTH_SHORT).show()

        backPressedTime = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> this@EventActivity extOpenActivity TicketActivity::class.java
            R.id.action_info -> this@EventActivity extOpenActivity InfoActivity::class.java
        }
        return super.onOptionsItemSelected(item)
    }

    fun endSession() {
        this extToast getString(R.string.login_again)
        finish()
        eventViewModel.endSession()
        this extOpenActivity LoginActivity::class.java
    }
}
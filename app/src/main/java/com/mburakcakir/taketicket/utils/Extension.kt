package com.mburakcakir.taketicket.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.ui.ticket.TicketActivity
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.dialog_ticket.view.*

infix fun <T> Context.extOpenActivity(cls : Class<T>) {
    val intent = Intent(this,cls)
    startActivity(intent)
}

infix fun Context.extToast(message : String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Activity.extDetailDialog(ticketModel: TicketModel, eventViewModel: EventViewModel) {
    val alertDialog = AlertDialog.Builder(this)
    val dialog = layoutInflater.inflate(R.layout.dialog_ticket, null)
    alertDialog.setView(dialog)
    val mAlertDialog = alertDialog.show()
    mAlertDialog.setCancelable(true)

    val eventModel = eventViewModel.getEventById(ticketModel.eventID)
    Glide.with(this).load(eventModel.url).into(dialog.imgTicketImage)

    dialog.apply {
        txtTicketName.text = ticketModel.name
        txtTicketEmail.text = ticketModel.email
        txtTicketTitle.text = eventModel.title
        txtTicketPrice.text = eventModel.price
        txtTicketTime.text = eventModel.time
    }

    dialog.btnApprove.setOnClickListener {
        if (!eventViewModel.checkIfTicketExists(ticketModel.ticketID)) {
            eventViewModel.insertTicket(ticketModel)
            this extToast this.getString(R.string.success_ticket)
            this extOpenActivity TicketActivity::class.java
        } else
            this extToast this.getString(R.string.warn_ticket)

        mAlertDialog.dismiss()

    }
}

infix fun Context.shareText(text: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    this.startActivity(shareIntent)
}
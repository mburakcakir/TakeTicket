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
import com.mburakcakir.taketicket.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.dialog_ticket.view.*

infix fun <T> Context.extOpenActivity(cls : Class<T>) {
    val intent = Intent(this,cls)
    startActivity(intent)
}

infix fun Context.extToast(message : String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Activity.extDetailDialog(ticketModel: TicketModel, homeViewModel: HomeViewModel) {
    val alertDialog = AlertDialog.Builder(this)
    val dialog = layoutInflater.inflate(R.layout.dialog_ticket, null)
    alertDialog.setView(dialog)
    val mAlertDialog = alertDialog.show()
    mAlertDialog.setCancelable(true)

    Glide.with(this).load(ticketModel.url).into(dialog.imgTicketImage)

    dialog.apply {
        txtTicketName.text = ticketModel.name
        txtTicketEmail.text = ticketModel.email
        txtTicketTitle.text = ticketModel.title
        txtTicketPrice.text = "Toplam : ${ticketModel.price}"
        txtTicketTime.text = ticketModel.time
    }

    dialog.btnApprove.setOnClickListener {
        homeViewModel.insertTicket(ticketModel)
        mAlertDialog.dismiss()
        this extToast "Biletin alındı."
        this extOpenActivity TicketActivity::class.java
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
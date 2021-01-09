package com.mburakcakir.taketicket.ui.event

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.dialog_ticket.view.*

class DetailDialog(
    private val ticketModel: TicketModel,
    private val onClickApproved: () -> Unit,
    private val onClickDenied: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_ticket, null)

        val mAlertDialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialog)
        }.show().also {
            isCancelable = true
        }

        val eventModel = eventViewModel.getEventById(ticketModel.eventID)
        Glide.with(requireContext()).load(eventModel.url).into(dialog.imgTicketImage)

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
                onClickApproved()
            } else
                onClickDenied()

            mAlertDialog.dismiss()
        }
        return mAlertDialog
    }

}

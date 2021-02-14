package com.mburakcakir.taketicket.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.DialogTicketBinding
import com.mburakcakir.taketicket.ui.ticket.TicketActivity
import com.mburakcakir.taketicket.ui.ticket.TicketViewModel
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast

class DetailDialog(
    private val ticketModel: TicketModel,
) : BottomSheetDialogFragment() {
    lateinit var binding: DialogTicketBinding

    // First way to implement
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTicketBinding.inflate(inflater, container, false)
        val dialogView = binding.root
        isCancelable = true

        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        val eventModel = eventViewModel.getEventById(ticketModel.eventID)
        Glide.with(requireContext()).load(eventModel.url).into(binding.imgTicketImage)

        binding.txtTicketName.text = ticketModel.name
        binding.txtTicketEmail.text = ticketModel.email
        binding.txtTicketTitle.text = eventModel.title
        binding.txtTicketPrice.text = eventModel.price
        binding.txtTicketTime.text = eventModel.time

        binding.btnApprove.setOnClickListener {
            ticketViewModel.insertTicket(ticketModel)
            dismiss()
        }

        ticketViewModel.newTicketResult.observe(requireActivity(), {
            when {
                it.success != null -> {
                    requireContext() extToast it.success
                    requireContext() extOpenActivity TicketActivity::class.java
                }
                it.error != null -> requireContext() extToast it.error
                it.loading != null -> requireContext() extToast it.loading
                it.warning != null -> requireContext() extToast it.warning
            }
        })
//        binding.btnApprove.setOnClickListener {
//            if (eventViewModel.checkIfTicketExists(ticketModel.ticketID)) {
//                eventViewModel.insertTicket(ticketModel)
//                onClickApproved()
//            } else
//                onClickDenied()
//
//            dismiss()
//        }
        return dialogView
    }

// Second way to implement
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
//        val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_ticket, null)
//
//        val mAlertDialog = AlertDialog.Builder(requireContext()).apply {
//            setView(dialog)
//        }.show().also {
//            isCancelable = true
//        }
//        mAlertDialog.window!!.setGravity(Gravity.BOTTOM)
//
//        val eventModel = eventViewModel.getEventById(ticketModel.eventID)
//        Glide.with(requireContext()).load(eventModel.url).into(dialog.imgTicketImage)
//
//        dialog.apply {
//            txtTicketName.text = ticketModel.name
//            txtTicketEmail.text = ticketModel.email
//            txtTicketTitle.text = eventModel.title
//            txtTicketPrice.text = eventModel.price
//            txtTicketTime.text = eventModel.time
//        }
//
//        dialog.btnApprove.setOnClickListener {
//            if (!eventViewModel.checkIfTicketExists(ticketModel.ticketID)) {
//                eventViewModel.insertTicket(ticketModel)
//                onClickApproved()
//            } else
//                onClickDenied()
//
//            mAlertDialog.dismiss()
//        }
//        return mAlertDialog
//    }
}

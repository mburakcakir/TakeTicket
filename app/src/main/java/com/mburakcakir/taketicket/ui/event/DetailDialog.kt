package com.mburakcakir.taketicket.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.DialogTicketBinding
import com.mburakcakir.taketicket.ui.ticket.TicketViewModel
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.navigate

class DetailDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogTicketBinding
    private val args by navArgs<DetailDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTicketBinding.inflate(inflater, container, false)
        val dialogView = binding.root
        isCancelable = true

        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        val ticketModel = args.ticketModel
        val eventModel = eventViewModel.getEventById(ticketModel.eventID)
        Glide.with(requireContext()).load(eventModel.url).into(binding.imgTicketImage)

        binding.txtTicketUsername.text = ticketModel.username
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
                    this.navigate(R.id.action_detailDialog_to_ticketFragment)
                }
                it.error != null -> requireContext() extToast it.error
                it.loading != null -> requireContext() extToast it.loading
                it.warning != null -> requireContext() extToast it.warning
            }
        })
        return dialogView
    }
}

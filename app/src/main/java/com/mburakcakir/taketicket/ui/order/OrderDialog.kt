package com.mburakcakir.taketicket.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mburakcakir.taketicket.databinding.DialogOrderBinding
import com.mburakcakir.taketicket.ui.event.turkish.TurkishEventViewModel
import com.mburakcakir.taketicket.util.navigate
import com.mburakcakir.taketicket.util.toast

class OrderDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogOrderBinding
    private lateinit var turkishEventViewModel: TurkishEventViewModel
    private lateinit var orderViewModel: OrderViewModel
    private val args by navArgs<OrderDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOrderBinding.inflate(inflater, container, false)
        isCancelable = true

        init()

        return binding.root
    }

    private fun init() {

        turkishEventViewModel = ViewModelProvider(this).get(TurkishEventViewModel::class.java)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val ticketModel = args.ticketModel
        val eventModel = turkishEventViewModel.getEventById(ticketModel.eventID)

        binding.apply {
            txtTicketUsername.text = ticketModel.username
            txtTicketEmail.text = ticketModel.email
            txtTicketTitle.text = eventModel.title
            txtTicketPrice.text = eventModel.price
            txtTicketTime.text = eventModel.time
            Glide.with(requireContext()).load(eventModel.url).into(imgTicketImage)
        }

        binding.imgTicketIncrease.setOnClickListener {
            orderViewModel.updateCountTicket(+1)
        }

        binding.imgTicketDecrease.setOnClickListener {
            orderViewModel.updateCountTicket(-1)
        }

        binding.btnApprove.setOnClickListener {
            orderViewModel.insertTicket(ticketModel)
            dismiss()
        }

        orderViewModel.countTicket.observe(this) {
            binding.ticketCount.text = it
        }

        orderViewModel.result.observe(requireActivity(), {
            when {
                it.success != null -> {
                    requireContext() toast it.success
                    this.navigate(OrderDialogDirections.actionDetailDialogToTicketFragment())
                }
                it.error != null -> requireContext() toast it.error
                it.loading != null -> requireContext() toast it.loading
                it.warning != null -> requireContext() toast it.warning
            }
        })
    }
}

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
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.util.extToast
import com.mburakcakir.taketicket.util.navigate

class OrderDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogOrderBinding
    private lateinit var eventViewModel: EventViewModel
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

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val ticketModel = args.ticketModel
        val eventModel = eventViewModel.getEventById(ticketModel.eventID)

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
                    requireContext() extToast it.success
                    this.navigate(OrderDialogDirections.actionDetailDialogToTicketFragment())
                }
                it.error != null -> requireContext() extToast it.error
                it.loading != null -> requireContext() extToast it.loading
                it.warning != null -> requireContext() extToast it.warning
            }
        })
    }
}

package com.mburakcakir.taketicket.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.DialogOrderBinding
import com.mburakcakir.taketicket.util.getCurrentTime
import com.mburakcakir.taketicket.util.navigate
import com.mburakcakir.taketicket.util.toast

class OrderBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private val args by navArgs<OrderBottomSheetDialogArgs>()

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet as View).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        isCancelable = true

        init()

        return binding.root
    }

    private fun init() {

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val eventModel = args.eventModel

        val ticketModel = TicketModel(
            orderViewModel.sessionManager.getUsername(),
            orderViewModel.sessionManager.getUserEmail(),
            getCurrentTime(),
            eventModel.eventID,
            eventModel.type
        )

        binding.apply {
            ticket = ticketModel
            event = eventModel
            viewmodel = orderViewModel
        }

        orderViewModel.result.observe(viewLifecycleOwner, {
            when {
                it.success != null -> {
                    requireContext() toast it.success
                    this.navigate(OrderBottomSheetDialogDirections.actionDetailDialogToTicketFragment())
                }
                it.error != null -> requireContext() toast it.error
                it.loading != null -> requireContext() toast it.loading
                it.warning != null -> requireContext() toast it.warning
            }
        })
    }
}

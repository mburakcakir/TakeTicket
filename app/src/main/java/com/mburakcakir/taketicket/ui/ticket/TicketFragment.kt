package com.mburakcakir.taketicket.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.databinding.FragmentTicketBinding
import com.mburakcakir.taketicket.utils.extToast


class TicketFragment : Fragment() {
    private lateinit var ticketViewModel: TicketViewModel
    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun init() {
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        binding.rvTicket.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvTicket.adapter = TicketAdapter(ticketViewModel.allEvents.value!!) {
            ticketViewModel.deleteTicket(it.ticketID)
        }

        ticketViewModel.allTickets.observe(requireActivity(), {
            (binding.rvTicket.adapter as TicketAdapter).submitList(it)
        })

        ticketViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> message = it.success
                !it.error.isNullOrEmpty() -> message = it.error
                !it.loading.isNullOrEmpty() -> message = it.loading
                !it.warning.isNullOrEmpty() -> message = it.warning
            }
            requireContext() extToast message
        })
    }
}
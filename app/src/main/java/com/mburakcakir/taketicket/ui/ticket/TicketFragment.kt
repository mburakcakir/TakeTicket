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
    private lateinit var binding: FragmentTicketBinding
    var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
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
                it.success != null -> message = it.success
                it.error != null -> message = it.error
                it.loading != null -> message = it.loading
                it.warning != null -> message = it.warning
            }
            requireContext() extToast message
        })
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_list -> this.navigate(R.id.action_eventFragment_to_ticketFragment)
//            R.id.action_info -> this.navigate(R.id.action_ticketFragment_to_infoFragment)
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
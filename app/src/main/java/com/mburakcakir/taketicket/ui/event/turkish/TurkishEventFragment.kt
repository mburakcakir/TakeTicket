package com.mburakcakir.taketicket.ui.event.turkish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.FragmentTurkishEventBinding
import com.mburakcakir.taketicket.ui.MainActivity
import com.mburakcakir.taketicket.util.toast

class TurkishEventFragment : Fragment() {
    private lateinit var turkishEventViewModel: TurkishEventViewModel
    private var _binding: FragmentTurkishEventBinding? = null
    private val binding get() = _binding!!
    private var eventAdapter: EventAdapter = EventAdapter()
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTurkishEventBinding.inflate(inflater, container, false)
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

    private fun init() {
        turkishEventViewModel = ViewModelProvider(this).get(TurkishEventViewModel::class.java)
        (requireActivity() as MainActivity).changeToolbarVisibility(View.VISIBLE)

        binding.rvEvent.adapter = eventAdapter

        eventAdapter.setEventOnClickListener {
            onClickEvent.invoke(it)
        }

        turkishEventViewModel.allEvents.observe(requireActivity(), { allEvents ->
            allEvents?.let {
                eventAdapter.submitList(allEvents)
            }
        })

        turkishEventViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> it.success
                !it.loading.isNullOrEmpty() -> it.loading
                else -> it.error
            }?.let { message ->
                requireContext() toast message
            }
        })
    }

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }
}
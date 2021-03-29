package com.mburakcakir.taketicket.ui.event.foreign

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.FragmentForeignEventBinding
import com.mburakcakir.taketicket.util.toast

class ForeignEventFragment : Fragment() {
    private var _binding: FragmentForeignEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var foreignEventViewModel: ForeignEventViewModel
    private val foreignEventAdapter: ForeignEventAdapter = ForeignEventAdapter()
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForeignEventBinding.inflate(inflater, container, false)

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
        foreignEventViewModel = ViewModelProvider(this).get(ForeignEventViewModel::class.java)
        binding.rvForeignEvent.adapter = foreignEventAdapter

        foreignEventViewModel.foreignEvents.observe(viewLifecycleOwner) { allEvents ->
            allEvents?.let {
                foreignEventAdapter.submitList(allEvents.results)
            }
            Log.v("data", allEvents.toString())
        }

        foreignEventAdapter.setEventOnClickListener {
            Log.v("onClickEventSet", this.onClickEvent.toString())
            onClickEvent.invoke(it)
        }

        foreignEventViewModel.result.observe(requireActivity(), {
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
        Log.v("onClickEvent", this.onClickEvent.toString())
    }
}
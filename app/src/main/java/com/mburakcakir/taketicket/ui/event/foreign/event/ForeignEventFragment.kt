package com.mburakcakir.taketicket.ui.event.foreign.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.databinding.FragmentForeignEventBinding

class ForeignEventFragment : Fragment() {
    private var _binding: FragmentForeignEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var foreignEventViewModel: ForeignEventViewModel
    private var foreignEventAdapter = ForeignEventAdapter()
    private lateinit var onClickEvent: (EventModel) -> Unit

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

        binding.rvEvent.adapter = foreignEventAdapter
        foreignEventAdapter.setEventOnClickListener { eventModel ->
            foreignEventViewModel.insertEvent(eventModel)
            onClickEvent.invoke(eventModel)
        }

        foreignEventViewModel.foreignEvents.observe(viewLifecycleOwner) { foreignEvents ->
            foreignEvents?.let {
                foreignEventAdapter.submitList(foreignEvents.events)
            }
        }
    }

    fun setOnEventClickListener(onClickEvent: (EventModel) -> Unit) {
        this.onClickEvent = onClickEvent
        Log.v("onClickEvent", this.onClickEvent.toString())
    }
}
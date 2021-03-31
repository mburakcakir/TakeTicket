package com.mburakcakir.taketicket.ui.event.turkish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.databinding.FragmentTurkishEventBinding
import com.mburakcakir.taketicket.util.toast

class TurkishEventFragment : Fragment() {
    private lateinit var turkishEventViewModel: TurkishEventViewModel
    private var _binding: FragmentTurkishEventBinding? = null
    private val binding get() = _binding!!
    private val turkishEventAdapter: TurkishEventAdapter = TurkishEventAdapter()
    private lateinit var onClickEvent: (EventModel) -> Unit

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

        binding.rvEvent.adapter = turkishEventAdapter
        turkishEventAdapter.setEventOnClickListener { eventModel ->
            onClickEvent.invoke(eventModel)
        }

        turkishEventViewModel.turkishEvents.observe(viewLifecycleOwner, { allEvents ->
            allEvents?.let {
                turkishEventAdapter.submitList(allEvents)
            }
        })

        turkishEventViewModel.result.observe(viewLifecycleOwner, {
            when {
                !it.success.isNullOrEmpty() -> it.success
                !it.loading.isNullOrEmpty() -> it.loading
                else -> it.error
            }?.let { message ->
                requireContext() toast message
            }
        })
    }

    fun setOnEventClickListener(onClickEvent: (EventModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }
}
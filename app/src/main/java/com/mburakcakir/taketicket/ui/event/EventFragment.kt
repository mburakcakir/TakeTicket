package com.mburakcakir.taketicket.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.FragmentEventBinding
import com.mburakcakir.taketicket.ui.MainActivity
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.util.extToast
import com.mburakcakir.taketicket.util.navigate

class EventFragment : Fragment() {
    private lateinit var eventViewModel: EventViewModel
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private var eventAdapter: EventAdapter = EventAdapter()
    private var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        checkIfUserLoggedIn()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkIfUserLoggedIn() {
        if (!eventViewModel.sessionManager.ifUserLoggedIn())
            this.navigate(EventFragmentDirections.actionEventFragmentToLoginFragment())
        else
            init()
    }

    private fun init() {
        (requireActivity() as MainActivity).changeToolbarVisibility(View.VISIBLE)

        binding.rvEvent.adapter = eventAdapter

        eventAdapter.setEventOnClickListener {
            EventFragmentDirections.actionEventFragmentToDetailDialog(it).let { direction ->
                this.navigate(direction)
            }
        }

        eventViewModel.allEvents.observe(requireActivity(), { allEvents ->
            allEvents?.let {
                eventAdapter.submitList(allEvents)
                Log.d("tag2", allEvents.toString())
            }
        })

        eventViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> it.success
                !it.loading.isNullOrEmpty() -> it.loading
                else -> it.error
            }?.let { message ->
                requireContext() extToast message
            }
        })

    }
}
package com.mburakcakir.taketicket.ui.event

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        init()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        (requireActivity() as MainActivity).apply {
            changeToolbarVisibility(View.VISIBLE)
            changeToolbarProfileUri(Uri.parse(eventViewModel.sessionManager.getImageUri()))
        }

        binding.rvEvent.adapter = eventAdapter

        binding.rvEvent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        eventAdapter.setOnClickEvent {
            EventFragmentDirections.actionEventFragmentToDetailDialog(it).let { navDirection ->
                this.navigate(navDirection)
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
                !it.success.isNullOrEmpty() -> message = it.success
                !it.error.isNullOrEmpty() -> message = it.error
                !it.loading.isNullOrEmpty() -> message = it.loading
            }
            requireContext() extToast message
        })

//        eventViewModel.result.observe(requireActivity(), { result ->
//            result.success?.let {
//                message = it
//            }
//            result.error?.let {
//                message = it
//            }
//            result.loading?.let {
//                message = it
//            }
//            requireContext() extToast message
//        })
    }
}
package com.mburakcakir.taketicket.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.FragmentEventBinding
import com.mburakcakir.taketicket.ui.MainActivity
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.navigate

class EventFragment : Fragment() {
    private lateinit var eventViewModel: EventViewModel
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private var adapter: EventAdapter = EventAdapter()
    var message: String = ""
    var backPressedTime: Long = 0

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

    fun init() {
        (requireActivity() as MainActivity).changeToolbarVisibility(View.VISIBLE)
        setHasOptionsMenu(true)
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(backpressedCallback)

        (requireActivity() as MainActivity).apply {
            changeToolbarVisibility(View.VISIBLE)
            changeToolbarTitle("Hoşgeldin ${eventViewModel.getUsername()}")
        }

        binding.rvEvent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.setOnClickEvent {
            EventFragmentDirections.actionEventFragmentToDetailDialog(it).let { navDirection ->
                this.navigate(navDirection)
            }
        }
        binding.rvEvent.adapter = adapter


        eventViewModel.allEvents.observe(requireActivity(), { allEvents ->
            allEvents?.let {
                (binding.rvEvent.adapter as EventAdapter).submitList(allEvents)
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


    private val backpressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backPressedTime + 5000 > System.currentTimeMillis())
                endSession()
            else
                requireContext() extToast getString(R.string.exit_app)

            backPressedTime = System.currentTimeMillis()
        }

    }

    fun endSession() {
        requireContext() extToast getString(R.string.login_again)
        eventViewModel.endSession()
        (requireActivity() as MainActivity).changeToolbarVisibility(View.GONE)
        this.navigate(R.id.action_eventFragment_to_loginFragment)
    }
}
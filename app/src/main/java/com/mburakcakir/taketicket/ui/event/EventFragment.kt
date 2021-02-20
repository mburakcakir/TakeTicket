package com.mburakcakir.taketicket.ui.event

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.databinding.FragmentEventBinding
import com.mburakcakir.taketicket.ui.viewmodel.EventViewModel
import com.mburakcakir.taketicket.utils.extToast
import kotlinx.android.synthetic.main.activity_welcome.*

class EventFragment : Fragment() {
    private lateinit var eventViewModel: EventViewModel
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    var message: String = ""
    var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        requireActivity().toolbar.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        requireActivity().toolbar.title = "Hoşgeldin ${eventViewModel.getUsername()}"

        binding.rvEvent.adapter = EventAdapter {
            DetailDialog(
                ticketModel = it
            ).show(requireActivity().supportFragmentManager, "DetailDialog")
        }
        binding.rvEvent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        eventViewModel.allEvents.observe(requireActivity(), { allEvents ->
            allEvents?.let {
                (binding.rvEvent.adapter as EventAdapter).submitList(allEvents)
                Log.d("tag2", allEvents.toString())
            }
        })

        eventViewModel.result.observe(requireActivity(), {
            when {
                it.success != null -> message = it.success
                it.error != null -> message = it.error
                it.loading != null -> message = it.loading
                it.warning != null -> message = it.warning
            }
            requireContext() extToast message
        })
    }

//    override fun onBackPressed() {
//        if (backPressedTime + 5000 > System.currentTimeMillis())
//            endSession()
//        else
//            Toast.makeText(requireContext(), getString(R.string.exit_app), Toast.LENGTH_SHORT).show()
//
//        backPressedTime = System.currentTimeMillis()
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_list -> this.navigate(R.id.action_eventFragment_to_ticketFragment)
//            R.id.action_info -> this.navigate(R.id.action_eventFragment_to_infoFragment)
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    fun endSession() {
//        requireContext() extToast getString(R.string.login_again)
//        finish()
//        eventViewModel.endSession()
//        this extOpenActivity LoginActivity::class.java
//    }
}
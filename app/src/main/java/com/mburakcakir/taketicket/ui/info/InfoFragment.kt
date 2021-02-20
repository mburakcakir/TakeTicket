package com.mburakcakir.taketicket.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.databinding.FragmentInfoBinding
import com.mburakcakir.taketicket.utils.extToast

class InfoFragment : Fragment() {
    private lateinit var infoViewModel: InfoViewModel
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        binding.rvInfo.adapter = InfoAdapter()
        binding.rvInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        infoViewModel.result.observe(requireActivity(), {
            if (it.success != null)
                requireContext() extToast it.success
            if (it.error != null)
                requireContext() extToast it.error
            if (it.loading != null)
                requireContext() extToast it.loading
        })
        infoViewModel.allInfo.observe(requireActivity(), {
            (binding.rvInfo.adapter as InfoAdapter).submitList(it)
        })

//        viewModel.allInfo.observe(this, {
//            it?.let {
//                (binding.rvInfo.adapter as InfoAdapter).submitList(it)
//                Log.d("tag2", it.toString())
//            }
//        })
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_list -> this.navigate(R.id.action_infoFragment_to_ticketFragment)
//            R.id.action_info -> this.navigate(R.id.action_eventFragment_to_infoFragment)
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
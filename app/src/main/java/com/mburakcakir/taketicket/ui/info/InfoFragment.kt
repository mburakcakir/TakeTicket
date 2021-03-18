package com.mburakcakir.taketicket.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.FragmentInfoBinding
import com.mburakcakir.taketicket.util.toast

class InfoFragment : Fragment() {
    private lateinit var infoViewModel: InfoViewModel
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoAdapter = InfoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
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
        binding.rvInfo.adapter = infoAdapter
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        infoViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> it.success
                !it.loading.isNullOrEmpty() -> it.loading
                else -> it.error
            }?.let { message ->
                requireContext() toast message
            }
        })

        infoViewModel.allInfo.observe(requireActivity(), {
            infoAdapter.submitList(it)
        })
    }
}
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
    private var message: String = ""

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
        binding.rvInfo.adapter = InfoAdapter()
        binding.rvInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        infoViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> message = it.success
                !it.error.isNullOrEmpty() -> message = it.error
                !it.loading.isNullOrEmpty() -> message = it.loading
            }
            requireContext() extToast message
        })

        infoViewModel.allInfo.observe(requireActivity(), {
            (binding.rvInfo.adapter as InfoAdapter).submitList(it)
        })
    }
}
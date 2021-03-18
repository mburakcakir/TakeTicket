package com.mburakcakir.taketicket.ui.event.foreign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.FragmentForeignEventBinding

class ForeignEventFragment : Fragment() {
    private lateinit var _binding: FragmentForeignEventBinding
    private val binding get() = _binding
    private lateinit var foreignEventViewModel: ForeignEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForeignEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foreignEventViewModel = ViewModelProvider(this).get(ForeignEventViewModel::class.java)
    }

}
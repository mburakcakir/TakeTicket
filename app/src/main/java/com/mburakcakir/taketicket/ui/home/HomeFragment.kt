package com.mburakcakir.taketicket.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mburakcakir.taketicket.databinding.FragmentHomeBinding
import com.mburakcakir.taketicket.ui.MainActivity
import com.mburakcakir.taketicket.util.navigate

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeEventPagerAdapter: HomeEventPagerAdapter
    private val tabTitles = arrayOf("Yerel", "Yabancı", "Filmler")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        checkIfUserLoggedIn()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkIfUserLoggedIn() {
        if (!homeViewModel.sessionManager.ifUserLoggedIn()) {
            (requireActivity() as MainActivity).changeToolbarVisibility(View.GONE)
            this.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        } else
            init()
    }

    private fun init() {
        (requireActivity() as MainActivity).changeToolbarVisibility(View.VISIBLE)
        homeEventPagerAdapter = HomeEventPagerAdapter(this)

        homeEventPagerAdapter.setEventOnClickListener {
            this.navigate(HomeFragmentDirections.actionHomeFragmentToDetailDialog(it))
        }
        homeEventPagerAdapter.createFragment(0)

        binding.vpEvent.adapter = homeEventPagerAdapter
        TabLayoutMediator(binding.tabEvent, binding.vpEvent) { tab, position ->
            tab.text = tabTitles[position]
            binding.vpEvent.setCurrentItem(tab.position, true)
        }.attach()
    }
}
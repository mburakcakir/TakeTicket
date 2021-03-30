package com.mburakcakir.taketicket.ui.event.foreign

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.remote.model.event.MovieResult
import com.mburakcakir.taketicket.databinding.FragmentForeignEventBinding
import com.mburakcakir.taketicket.util.toast

class ForeignEventFragment : Fragment() {
    private var _binding: FragmentForeignEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var foreignEventViewModel: ForeignEventViewModel
    private val foreignEventAdapter: ForeignEventAdapter = ForeignEventAdapter()
    private var nestedRecyclerViewAdapter: NestedRecyclerViewAdapter = NestedRecyclerViewAdapter()
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit
    private var upcomingMovies: List<MovieResult> = emptyList()
    private var popularMovies: List<MovieResult> = emptyList()
    private var trendingMovies: List<MovieResult> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForeignEventBinding.inflate(inflater, container, false)

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
        foreignEventViewModel = ViewModelProvider(this).get(ForeignEventViewModel::class.java)

        foreignEventViewModel.upcomingEvents.observe(viewLifecycleOwner) { upcomingEvents ->
            upcomingEvents?.let {
                upcomingMovies = upcomingEvents.results
                setAdapter()
            }
        }

        foreignEventViewModel.popularEvents.observe(viewLifecycleOwner) { popularEvents ->
            popularEvents?.let {
                popularMovies = popularEvents.results
            }
        }

        foreignEventViewModel.trendingEvents.observe(viewLifecycleOwner) { trendingEvents ->
            trendingEvents?.let {
                trendingMovies = trendingEvents.results

            }
        }

        foreignEventAdapter.setEventOnClickListener {
            Log.v("onClickEventSet", this.onClickEvent.toString())
            onClickEvent.invoke(it)
        }

        foreignEventViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> it.success
                !it.loading.isNullOrEmpty() -> it.loading
                else -> it.error
            }?.let { message ->
                requireContext() toast message
            }
        })
    }

    fun setAdapter() {
        val categoryItemList = listOf<NestedRecyclerViewModel>(
            NestedRecyclerViewModel("Yaklaşan Etkinlikler", upcomingMovies),
            NestedRecyclerViewModel("Popüler Etkinlikler", popularMovies),
            NestedRecyclerViewModel("Yükselen Etkinlikler", trendingMovies)
        )
        nestedRecyclerViewAdapter.setCategoryList(categoryItemList)
        binding.rvForeignEvent.adapter = nestedRecyclerViewAdapter
    }

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
        Log.v("onClickEvent", this.onClickEvent.toString())
    }
}
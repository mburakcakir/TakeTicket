package com.mburakcakir.taketicket.ui.event.foreign.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.remote.model.movie.MovieResult
import com.mburakcakir.taketicket.databinding.FragmentForeignMovieBinding
import com.mburakcakir.taketicket.util.toast

class ForeignMovieFragment : Fragment() {
    private var _binding: FragmentForeignMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var foreignMovieViewModel: ForeignMovieViewModel
    private val foreignMovieAdapter: ForeignMovieAdapter = ForeignMovieAdapter()
    private var nestedRecyclerViewAdapter: NestedRecyclerViewAdapter = NestedRecyclerViewAdapter()
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit
    private var upcomingMovies: List<MovieResult> = emptyList()
    private var popularMovies: List<MovieResult> = emptyList()
    private var trendingMovies: List<MovieResult> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForeignMovieBinding.inflate(inflater, container, false)

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
        foreignMovieViewModel = ViewModelProvider(this).get(ForeignMovieViewModel::class.java)

        foreignMovieViewModel.upcomingEvents.observe(viewLifecycleOwner) { upcomingEvents ->
            upcomingEvents?.let {
                upcomingMovies = upcomingEvents.results
                setAdapter()
            }
        }

        foreignMovieViewModel.popularEvents.observe(viewLifecycleOwner) { popularEvents ->
            popularEvents?.let {
                popularMovies = popularEvents.results
            }
        }

        foreignMovieViewModel.trendingEvents.observe(viewLifecycleOwner) { trendingEvents ->
            trendingEvents?.let {
                trendingMovies = trendingEvents.results
            }
        }


        foreignMovieAdapter.setEventOnClickListener {
            Log.v("onClickEventSet", this.onClickEvent.toString())
            onClickEvent.invoke(it)
        }

        foreignMovieViewModel.result.observe(requireActivity(), {
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
        val categoryItemList = listOf<NestedViewModel>(
            NestedViewModel("Yaklaşan Etkinlikler", upcomingMovies),
            NestedViewModel("Popüler Etkinlikler", popularMovies),
            NestedViewModel("Trending Etkinlikler", trendingMovies)
        )
            nestedRecyclerViewAdapter.setCategoryList(categoryItemList)
            binding.rvForeignEvent.adapter = nestedRecyclerViewAdapter

    }

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
        Log.v("onClickEvent", this.onClickEvent.toString())
    }
}
package com.mburakcakir.taketicket.ui.event.foreign.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.remote.model.movie.MovieResult
import com.mburakcakir.taketicket.databinding.RvItemMainForeignMovieBinding

class NestedRecyclerViewAdapter : RecyclerView.Adapter<NestedRecyclerViewAdapter.MainViewHolder>() {

    lateinit var nestedViewModel: List<NestedViewModel>

    fun setCategoryList(nestedViewModel: List<NestedViewModel>) {
        this.nestedViewModel = nestedViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RvItemMainForeignMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(nestedViewModel[position])
    }

    override fun getItemCount(): Int = nestedViewModel.size

    fun setCategoryRecyclerView(
        recyclerView: RecyclerView,
        listCategory: List<MovieResult>,
    ) {
        val categoryItemAdapter = ForeignMovieAdapter()
        categoryItemAdapter.submitList(listCategory)
        recyclerView.adapter = categoryItemAdapter
        recyclerView.setHasFixedSize(true)
    }

    inner class MainViewHolder(
        private val binding: RvItemMainForeignMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nestedViewModel: NestedViewModel) {
            binding.txtHeader.text = nestedViewModel.title
            setCategoryRecyclerView(binding.rvItem, nestedViewModel.movieResult)
        }

    }
}


package com.mburakcakir.taketicket.ui.event.foreign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.remote.model.event.MovieResult
import com.mburakcakir.taketicket.databinding.RvItemMainBinding

class NestedRecyclerViewAdapter : RecyclerView.Adapter<NestedRecyclerViewAdapter.MainViewHolder>() {

    lateinit var nestedRecyclerViewModel: List<NestedRecyclerViewModel>

    fun setCategoryList(nestedRecyclerViewModel: List<NestedRecyclerViewModel>) {
        this.nestedRecyclerViewModel = nestedRecyclerViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RvItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(nestedRecyclerViewModel[position])
    }

    override fun getItemCount(): Int = nestedRecyclerViewModel.size

    fun setCategoryRecyclerView(
        recyclerView: RecyclerView,
        listCategory: List<MovieResult>,
    ) {
        val categoryItemAdapter = ForeignEventAdapter()
        categoryItemAdapter.submitList(listCategory)
        recyclerView.adapter = categoryItemAdapter
        recyclerView.setHasFixedSize(true)
    }

    inner class MainViewHolder(
        private val binding: RvItemMainBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nestedRecyclerViewModel: NestedRecyclerViewModel) {
            binding.txtHeader.text = nestedRecyclerViewModel.title
            setCategoryRecyclerView(binding.rvItem, nestedRecyclerViewModel.movieResult)
        }

    }
}


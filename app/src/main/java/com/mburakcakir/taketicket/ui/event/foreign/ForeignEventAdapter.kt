package com.mburakcakir.taketicket.ui.event.foreign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.remote.model.event.MovieResult
import com.mburakcakir.taketicket.databinding.RvItemForeignEventBinding

class ForeignEventAdapter :
    ListAdapter<MovieResult, ForeignEventAdapter.ForeignEventViewHolder>(ForeignEventCallback()) {
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeignEventViewHolder {
        return ForeignEventViewHolder(
            RvItemForeignEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderForeign: ForeignEventViewHolder, position: Int) =
        holderForeign.bind(getItem(position))

    inner class ForeignEventViewHolder(
        private val binding: RvItemForeignEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieResult: MovieResult) {
            binding.movie = movieResult

        }
    }

}

class ForeignEventCallback : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return false
    }
}




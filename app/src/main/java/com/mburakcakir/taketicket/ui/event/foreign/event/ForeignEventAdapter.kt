package com.mburakcakir.taketicket.ui.event.foreign.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.remote.model.event.ForeignEvent
import com.mburakcakir.taketicket.databinding.RvItemForeignEventBinding
import com.mburakcakir.taketicket.util.SessionManager

class ForeignEventAdapter :
    ListAdapter<ForeignEvent, ForeignEventAdapter.ForeignEventViewHolder>(ForeignEventCallback()) {

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
        fun bind(foreignEvent: ForeignEvent) {
            val sessionManager = SessionManager(itemView.context)
            binding.event = foreignEvent
        }
    }

}

class ForeignEventCallback : DiffUtil.ItemCallback<ForeignEvent>() {
    override fun areItemsTheSame(oldItem: ForeignEvent, newItem: ForeignEvent) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ForeignEvent, newItem: ForeignEvent): Boolean {
        return false
    }
}
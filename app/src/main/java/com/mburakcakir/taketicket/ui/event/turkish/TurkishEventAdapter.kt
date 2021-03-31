package com.mburakcakir.taketicket.ui.event.turkish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.databinding.RvItemTurkishEventBinding

class TurkishEventAdapter :
    ListAdapter<EventModel, TurkishEventAdapter.TurkishEventViewHolder>(TurkishEventCallback()) {
    private lateinit var onClickEvent: (EventModel) -> Unit

    fun setEventOnClickListener(onClickEvent: (EventModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurkishEventViewHolder {
        return TurkishEventViewHolder(
            RvItemTurkishEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderTurkish: TurkishEventViewHolder, position: Int) =
        holderTurkish.bind(getItem(position))

    inner class TurkishEventViewHolder(
        private val binding: RvItemTurkishEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventModel: EventModel) {

            binding.event = eventModel
            itemView.setOnClickListener {
                onClickEvent(eventModel)
            }
        }
    }
}

class TurkishEventCallback : DiffUtil.ItemCallback<EventModel>() {
    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return false
    }
}




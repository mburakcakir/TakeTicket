package com.mburakcakir.taketicket.ui.event.turkish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.RvItemEventBinding
import com.mburakcakir.taketicket.util.SessionManager
import com.mburakcakir.taketicket.util.getCurrentTime

class EventAdapter : ListAdapter<EventModel, EventViewHolder>(EventCallback()) {
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            RvItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickEvent
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class EventCallback : DiffUtil.ItemCallback<EventModel>() {
    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return false
    }
}

class EventViewHolder(
    private val binding: RvItemEventBinding,
    private val onClickEvent: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(eventModel: EventModel) {
        val sessionManager = SessionManager(itemView.context)

        binding.event = eventModel

        itemView.setOnClickListener {
            val ticketModel = TicketModel(
                sessionManager.getUsername(),
                sessionManager.getUserEmail(),
                getCurrentTime(),
                eventModel.eventID
            )
            onClickEvent(ticketModel)
        }
    }
}


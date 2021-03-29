package com.mburakcakir.taketicket.ui.event.turkish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.RvItemTurkishEventBinding
import com.mburakcakir.taketicket.util.SessionManager
import com.mburakcakir.taketicket.util.getCurrentTime

class TurkishEventAdapter :
    ListAdapter<EventModel, TurkishEventAdapter.TurkishEventViewHolder>(TurkishEventCallback()) {
    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit

    fun setEventOnClickListener(onClickEvent: (TicketModel) -> Unit) {
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

}

class TurkishEventCallback : DiffUtil.ItemCallback<EventModel>() {
    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return false
    }
}




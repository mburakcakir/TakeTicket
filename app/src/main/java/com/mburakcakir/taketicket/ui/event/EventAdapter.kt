package com.mburakcakir.vbtinternshipschedule.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.getCurrentTime
import kotlinx.android.synthetic.main.rv_item_event.view.*

class EventAdapter(
    private inline val onClickEvent: (ticketModel: TicketModel) -> Unit
) : ListAdapter<EventModel, EventViewHolder>(EventCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(parent, onClickEvent)

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
    container: ViewGroup,
    private val onClickEvent: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate(
        R.layout.rv_item_event,
        container,
        false
    )
) {
    fun bind(eventModel: EventModel) {
        val sessionManager = SessionManager(itemView.context)
        with(itemView) {
            txtEventTitle.text = "${eventModel.title}"
            txtEventSubtitle.text = eventModel.subTitle
            txtEventTime.text = eventModel.time
            txtEventOrder.text = eventModel.category
            txtEventCapacity.text = "Kapasite: ${eventModel.capacity}"
            Glide.with(itemView.context).load(eventModel.url).into(imgEventImage)
        }
        itemView.setOnClickListener {

            val ticketModel = TicketModel(
                sessionManager.getName()!!,
                sessionManager.getUserEmail()!!,
                eventModel.title,
                eventModel.price,
                eventModel.time,
                eventModel.category,
                getCurrentTime(),
                eventModel.url,
            )
            onClickEvent(ticketModel)
        }
    }

}


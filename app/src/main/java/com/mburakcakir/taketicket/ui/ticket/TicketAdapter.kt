package com.mburakcakir.taketicket.ui.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.databinding.RvItemTicketBinding

class TicketAdapter : ListAdapter<TicketModel, TicketViewHolder>(TicketCallback()) {

    private lateinit var onClickEvent: (ticketModel: TicketModel) -> Unit
    private lateinit var eventList: List<EventModel>

    fun setOnClickEvent(onClickEvent: (TicketModel) -> Unit) {
        this.onClickEvent = onClickEvent
    }

    fun setEventList(eventList: List<EventModel>) {
        this.eventList = eventList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder(
            RvItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            eventList,
            onClickEvent
        )
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class TicketCallback : DiffUtil.ItemCallback<TicketModel>() {
    override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel) =
        oldItem.ticketID == newItem.ticketID

    override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel) = oldItem == newItem

}

class TicketViewHolder(
    private val binding: RvItemTicketBinding,
    private val eventList: List<EventModel>,
    private val onClick: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ticketModel: TicketModel) {
        val eventModel = eventList[ticketModel.eventID - 1]
        with(binding) {

            txtEventTitle.text = eventModel.title
            txtEventPrice.text = eventModel.price
            txtTicketLastTime.text = eventModel.time
            txtTicketCategory.text = eventModel.category
            txtTicketBoughtTime.text = ticketModel.boughtTime
            Glide.with(itemView.context).load(eventModel.url).into(imgTicketLastImage)

            imgDeleteTicket.setOnClickListener {
                onClick(ticketModel)
            }

        }
    }

}
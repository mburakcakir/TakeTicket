package com.mburakcakir.taketicket.ui.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import kotlinx.android.synthetic.main.rv_item_ticket.view.*

class TicketAdapter(
    private val ticketViewModel: TicketViewModel,
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : ListAdapter<TicketModel, TicketViewHolder>(TicketCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TicketViewHolder(parent, ticketViewModel, onClick)

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class TicketCallback : DiffUtil.ItemCallback<TicketModel>() {
    override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel) = false

}

class TicketViewHolder(
    container: ViewGroup,
    private val ticketViewModel: TicketViewModel,
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate(
        R.layout.rv_item_ticket,
        container,
        false
    )
) {
    fun bind(ticketModel: TicketModel) {
        val eventModel = ticketViewModel.getEventByID(ticketModel.eventID)
        with(itemView) {
            txtTicketTitle.text = eventModel.title
            txtTicketPrice.text = eventModel.price
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
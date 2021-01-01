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
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : ListAdapter<TicketModel, TicketViewHolder>(TicketCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TicketViewHolder(parent, onClick)

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
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate(
        R.layout.rv_item_ticket,
        container,
        false
    )
) {
    fun bind(ticketModel: TicketModel) {
        with(itemView) {
            txtTicketTitle.text = ticketModel.title
            txtTicketPrice.text = ticketModel.price
            txtTicketLastTime.text = ticketModel.time
            txtTicketCategory.text = ticketModel.category
            txtTicketBoughtTime.text = ticketModel.boughtTime
            Glide.with(itemView.context).load(ticketModel.url).into(imgTicketLastImage)

            imgDeleteTicket.setOnClickListener {
                onClick(ticketModel)

            }
        }
    }

}
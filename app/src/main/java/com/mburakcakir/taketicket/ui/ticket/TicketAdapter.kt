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

class TicketAdapter(
    private val eventList: List<EventModel>,
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : ListAdapter<TicketModel, TicketViewHolder>(TicketCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding =
            RvItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding, eventList, onClick)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class TicketCallback : DiffUtil.ItemCallback<TicketModel>() {
    override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel) = false

}

class TicketViewHolder(
    private val binding: RvItemTicketBinding,
    private val eventList: List<EventModel>,
    private inline val onClick: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticketModel: TicketModel) {
        val eventModel = eventList[ticketModel.eventID - 1]

        with(binding) {
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
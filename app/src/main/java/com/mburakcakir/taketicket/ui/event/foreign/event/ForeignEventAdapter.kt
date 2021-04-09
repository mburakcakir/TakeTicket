package com.mburakcakir.taketicket.ui.event.foreign.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.data.remote.model.event.ForeignEvent
import com.mburakcakir.taketicket.databinding.RvItemForeignEventBinding

class ForeignEventAdapter :
    ListAdapter<ForeignEvent, ForeignEventAdapter.ForeignEventViewHolder>(ForeignEventCallback()) {
    private lateinit var onClickEvent: (EventModel) -> Unit
    private lateinit var eventModel: EventModel

    fun setEventOnClickListener(onClickEvent: (EventModel) -> Unit) {
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
        fun bind(foreignEvent: ForeignEvent) {

            val list = foreignEvent.datetime_utc.chunked(10)
//            val datetime = "${list[0]} ${list[1].substring(1, 6)}"
            val datetime = list[0]

            val copiedEvent = foreignEvent.copy(
                datetime_utc = datetime,
                type = foreignEvent.type.capitalize()
            )

            binding.event = copiedEvent


            itemView.setOnClickListener {
                foreignEvent.apply {
                    eventModel = EventModel(
                        id,
                        venue.name,
                        "${venue.address} ${venue.extended_address}",
                        type,
                        venue.capacity,
                        performers[0].image,
                        datetime,
                        performers[0].num_upcoming_events,
                        "Foreign"
                    )
                }
                onClickEvent(eventModel)
            }
        }
    }

}

class ForeignEventCallback : DiffUtil.ItemCallback<ForeignEvent>() {
    override fun areItemsTheSame(oldItem: ForeignEvent, newItem: ForeignEvent) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ForeignEvent, newItem: ForeignEvent): Boolean =
        oldItem == newItem

}
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

// Adapter Pattern olarak, Android Programlamada sıkça kullanılan RecyclerViewAdapter ve yeni kullanılmaya başlanılan ListAdapter sınıflarının ikisi de kullanıldı.
// RecyclerViewAdapter kullanırken de yine nesneleri adapter aracılığıyla View üzerinde yerleştirmekteyiz.
// onCreateViewHolder methodu, ViewHolder yani View ve değişkenleri yönetmemizi sağlayan sınıfımızı tanıtmamızı sağlıyor.
// getItemCount ile Adapter'a gelen listenin boyutunu ileterek kaç kez tekrar edeceğini belirtiyoruz
// onBindViewHolder ile, TicketViewHolder içerisinde bulunan bind methodunu tetikliyor.
// TicketViewHolder sınıfı ile View belirlenmesi, bind methodu ile List<Model> olarak gelen nesnelerin pozisyonlarının alınıp, Modellerin componentler üzerine yerleşmesini sağlanmaktadır.
// Burası Adapter'ın DataBinding görevini üstlendiği yerdir.
class TicketAdapter : ListAdapter<TicketModel, TicketViewHolder>(InfoCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TicketViewHolder(parent)

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) =
        holder.bind(getItem(position))

}


class InfoCallBack : DiffUtil.ItemCallback<TicketModel>() {
    override fun areItemsTheSame(oldItem: TicketModel, newItem: TicketModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TicketModel, newItem: TicketModel) = false

}

class TicketViewHolder(
    container: ViewGroup
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
        }
    }
}
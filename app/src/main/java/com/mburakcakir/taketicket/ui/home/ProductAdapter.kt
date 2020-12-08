package com.mburakcakir.vbtinternshipschedule.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.ProductModel
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.android.synthetic.main.rv_item_product.view.*
import java.text.SimpleDateFormat
import java.util.*

// Adapter Pattern olarak, Android Programlamada sıkça kullanılan RecyclerViewAdapter ve yeni kullanılmaya başlanılan ListAdapter sınıflarının ikisi de kullanıldı.
// ListAdapter kullanırken de yine nesneleri adapter aracılığıyla View üzerinde yerleştirmekteyiz.
// getItemCount ile Adapter'a gelen listenin boyutunu ileterek kaç kez tekrar edeceğini belirtiyoruz
// onBindViewHolder ile, ProductViewHolder içerisinde bulunan bind methodunu tetikliyor.
// ProductCallBack sınıfı, yeni gelen nesneleri eskisiyle karşılaştırıp reaksiyon alabilmemizi sağlıyor.
// ProductViewHolder sınıfı ile View belirlenmesi, bind methodu ile List<Model> olarak gelen nesnelerin pozisyonlarının alınıp, Modellerin componentler üzerine yerleşmesini sağlanmaktadır.
// Burası Adapter'ın DataBinding görevini üstlendiği yerdir.
class ProductAdapter(
    private inline val onClickEvent: (ticketModel: TicketModel) -> Unit
) : ListAdapter<ProductModel, ProductViewHolder>(ProductCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(parent, onClickEvent)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class ProductCallBack : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return false
    }
}

class ProductViewHolder(
    container: ViewGroup,
    private val onClickEvent: (ticketModel: TicketModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate(
        R.layout.rv_item_product,
        container,
        false
    )
) {
    fun bind(productModel: ProductModel) {
        val sessionManager = SessionManager(itemView.context)
        with(itemView) {
            txtProductTitle.text = "${productModel.title}"
            txtProductSubtitle.text = productModel.subTitle
            txtProductTime.text = productModel.time
            txtProductOrder.text = productModel.category
            txtProductCapacity.text = "Kapasite: ${productModel.capacity}"
                Glide.with(itemView.context).load(productModel.url).into(imgProductImage)
        }
        itemView.setOnClickListener {

            val ticketModel = TicketModel(
                sessionManager.getName()!!,
                sessionManager.getUserEmail()!!,
                productModel.title,
                productModel.price,
                productModel.time,
                productModel.category,
                getCurrentTime(),
                productModel.url,
            )
            onClickEvent(ticketModel)
        }
    }
    fun getCurrentTime(): String {
        val currentTime: Date = Calendar.getInstance().getTime()
        val dateFormat = SimpleDateFormat("hh.mm")
        return dateFormat.format(currentTime)
    }
}


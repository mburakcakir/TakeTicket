package com.mburakcakir.taketicket.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.utils.GlideApp
import com.mburakcakir.taketicket.utils.shareText
import kotlinx.android.synthetic.main.rv_item_info.view.*

// Adapter Pattern olarak, Android Programlamada sıkça kullanılan RecyclerViewAdapter ve yeni kullanılmaya başlanılan ListAdapter sınıflarının ikisi de kullanıldı.
// ListAdapter kullanırken de yine nesneleri adapter aracılığıyla View üzerinde yerleştirmekteyiz.
// getItemCount ile Adapter'a gelen listenin boyutunu ileterek kaç kez tekrar edeceğini belirtiyoruz
// onBindViewHolder ile, InfoViewHolder içerisinde bulunan bind methodunu tetikliyor.
// InfoCallBack sınıfı, yeni gelen nesneleri eskisiyle karşılaştırıp reaksiyon alabilmemizi sağlıyor.
// InfoViewHolder sınıfı ile View belirlenmesi, bind methodu ile List<Model> olarak gelen nesnelerin pozisyonlarının alınıp, Modellerin componentler üzerine yerleşmesini sağlanmaktadır.

class InfoAdapter : ListAdapter<InfoModel, InfoViewHolder>(InfoCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InfoViewHolder(parent)

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) =
        holder.bind(getItem(position))

}


class InfoCallBack : DiffUtil.ItemCallback<InfoModel>() {
    override fun areItemsTheSame(oldItem: InfoModel, newItem: InfoModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: InfoModel, newItem: InfoModel) = false

}

class InfoViewHolder(
    container: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate(
        R.layout.rv_item_info,
        container,
        false
    )
) {
    fun bind(infoModel: InfoModel) {
        val storage = FirebaseStorage.getInstance()
        val getReference = storage.getReferenceFromUrl(infoModel.imageUrl)

        with(itemView) {
            txtInfoTitle.text = infoModel.account
            txtInfoLink.text = infoModel.siteUrl
            GlideApp.with(context).load(getReference).into(imgInfoImage)
        }

        itemView.imgInfoShare.setOnClickListener {
            itemView.context shareText infoModel.siteUrl
        }
    }
}
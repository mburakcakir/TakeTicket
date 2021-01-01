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

            imgInfoShare.setOnClickListener {
                itemView.context shareText infoModel.siteUrl
            }
        }

    }
}
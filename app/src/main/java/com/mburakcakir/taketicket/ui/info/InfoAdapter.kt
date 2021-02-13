package com.mburakcakir.taketicket.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.databinding.RvItemInfoBinding
import com.mburakcakir.taketicket.utils.GlideApp
import com.mburakcakir.taketicket.utils.shareText


class InfoAdapter : ListAdapter<InfoModel, InfoViewHolder>(InfoCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = RvItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class InfoCallBack : DiffUtil.ItemCallback<InfoModel>() {
    override fun areItemsTheSame(oldItem: InfoModel, newItem: InfoModel) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: InfoModel, newItem: InfoModel) = false

}

class InfoViewHolder(
    private val binding: RvItemInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(infoModel: InfoModel) {
        val storage = FirebaseStorage.getInstance()
        val getReference = storage.getReferenceFromUrl(infoModel.imageUrl)

        with(binding) {
            txtInfoTitle.text = infoModel.account
            txtInfoLink.text = infoModel.siteUrl
            GlideApp.with(itemView.context).load(getReference).into(imgInfoImage)

            imgInfoShare.setOnClickListener {
                itemView.context shareText infoModel.siteUrl
            }
        }

    }
}
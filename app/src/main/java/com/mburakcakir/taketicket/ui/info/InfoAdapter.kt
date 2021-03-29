package com.mburakcakir.taketicket.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mburakcakir.taketicket.databinding.RvItemInfoBinding
import com.mburakcakir.taketicket.network.model.info.InfoModel


class InfoAdapter : ListAdapter<InfoModel, InfoViewHolder>(InfoCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            RvItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
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
        binding.info = infoModel
    }
}
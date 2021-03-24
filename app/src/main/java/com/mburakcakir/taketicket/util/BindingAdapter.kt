package com.mburakcakir.taketicket.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.mburakcakir.taketicket.data.db.entity.TicketModel

object BindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String) {
        Glide.with(context).load(url).into(this)
    }

    @BindingAdapter("loadImageFromUrlWithFirebase")
    @JvmStatic
    fun ImageView.loadImageFromFirebase(url: String) {
        val storage = FirebaseStorage.getInstance()
        val getReference = storage.getReferenceFromUrl(url)
        GlideApp.with(context).load(getReference).into(this)
    }

    @BindingAdapter("shareText")
    @JvmStatic
    fun ImageView.shareText(text: String) {
        context shareText text
    }

    @BindingAdapter("ticketOnClick")
    @JvmStatic
    fun ImageView.ticketOnClick(onClick: (ticketModel: TicketModel) -> Unit) {
        onClick
    }
}
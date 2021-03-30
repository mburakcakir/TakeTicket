package com.mburakcakir.taketicket.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context).load(imageUrl).into(this)
//    this.load(imageUrl)
}

@BindingAdapter("loadMovieImage")
fun ImageView.loadMovieImage(imageUrl: String) {
    val base = "https://image.tmdb.org/t/p/w500"
//    Glide.with(context).load(base + imageUrl).into(this)
    this.load(base + imageUrl)
}

@BindingAdapter("loadImageFromUrlWithFirebase")
fun ImageView.loadImageFromFirebase(url: String) {
    val storage = FirebaseStorage.getInstance()
    val reference = storage.getReferenceFromUrl(url)
    GlideApp.with(context).load(reference).into(this)
}

@BindingAdapter("shareText")
fun ImageView.shareText(text: String) {
    context shareText text
}

//@BindingAdapter("loginViewModel", "text", "entryState", requireAll = true)
//fun EditText.afterTextChanged(
//    loginViewModel: LoginViewModel,
//    text: String,
//    entryState: EntryState
//) {
//    this.addTextChangedListener(object : CustomTextWatcher() {
//        override fun afterTextChanged(editable: Editable?) {
//            loginViewModel.isDataChanged(entryState, text)
//        }
//    })
//}


//object BindingAdapter {
//
////    @JvmStatic
////    @BindingAdapter("loginViewModel", "text", "entryState")
////    private fun EditText.afterTextChanged(loginViewModel: LoginViewModel, text: String, entryState: EntryState) {
////        addTextChangedListener(object : CustomTextWatcher() {
////            override fun afterTextChanged(editable: Editable?) {
////                loginViewModel.isDataChanged(entryState,text)
////            }
////        })
////    }
//}
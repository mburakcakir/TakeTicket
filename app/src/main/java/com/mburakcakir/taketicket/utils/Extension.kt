package com.mburakcakir.taketicket.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

infix fun Fragment.navigate(action: Int) {
    NavHostFragment.findNavController(this).navigate(action)
}

infix fun Fragment.navigate(navDirections: NavDirections) {
    NavHostFragment.findNavController(this).navigate(navDirections)
}

infix fun Context.extToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

infix fun Context.shareText(text: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    this.startActivity(shareIntent)
}
package com.mburakcakir.taketicket.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.mburakcakir.taketicket.R

infix fun Fragment.navigate(action: Int) {
    NavHostFragment.findNavController(this).navigate(action)
}

infix fun Fragment.navigate(navDirections: NavDirections) {
    NavHostFragment.findNavController(this).navigate(navDirections)
}

infix fun Context.toast(message: String) {
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

fun View.slideUp(animTime: Long, startOffSet: Long) {
    AnimationUtils.loadAnimation(context, R.anim.slide_out_top).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        startOffset = startOffSet
    }.run {
        startAnimation(this)
    }
}
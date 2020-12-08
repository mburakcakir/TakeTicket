package com.mburakcakir.taketicket.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.welcome.WelcomeActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(1500,1000) {
            override fun onTick(p0: Long) {
                appName.text = resources.getString(R.string.app_name)
            }

            override fun onFinish() {
                this@SplashActivity extOpenActivity WelcomeActivity::class.java
            }

        }.start()
    }
}
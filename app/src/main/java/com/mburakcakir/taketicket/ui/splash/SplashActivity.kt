package com.mburakcakir.taketicket.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.activity.EventActivity
import com.mburakcakir.taketicket.ui.login.LoginActivity
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.extOpenActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(1500, 1000) {
            override fun onTick(p0: Long) {
                appName.text = getString(R.string.app_name)
            }

            override fun onFinish() {
                sessionManager = SessionManager(this@SplashActivity)
                val state = sessionManager.ifUserLoggedIn()

                if (state)
                    this@SplashActivity extOpenActivity EventActivity::class.java
                else
                    this@SplashActivity extOpenActivity LoginActivity::class.java
                finish()
            }

        }.start()
    }
}
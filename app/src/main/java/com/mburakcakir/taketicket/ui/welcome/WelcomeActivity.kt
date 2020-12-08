package com.mburakcakir.taketicket.ui.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.activity.HomeActivity
import com.mburakcakir.taketicket.ui.login.LoginActivity
import com.mburakcakir.taketicket.ui.register.RegisterActivity
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.extOpenActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        sessionManager = SessionManager(this)
        val state = sessionManager.ifUserLoggedIn()
        if(state)
            extOpenActivity(HomeActivity::class.java)

        btnLogin.setOnClickListener {
            extOpenActivity(LoginActivity::class.java)
        }

        btnRegister.setOnClickListener {
            extOpenActivity(RegisterActivity::class.java)
        }
    }
}
package com.mburakcakir.taketicket.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.activity.EventActivity
import com.mburakcakir.taketicket.ui.register.RegisterActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.edtPassword
import kotlinx.android.synthetic.main.activity_register.edtUsername

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    fun init() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnRegister.setOnClickListener {
            extOpenActivity(RegisterActivity::class.java)
        }

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()
            val checkUser = loginViewModel.checkIfUserExists(username, password)
            if (checkUser) {
                loginViewModel.getUserByUsername(username, password).observe(this, {
                    loginViewModel.startSession(it)
                    Log.d("data", it.toString())
                    extOpenActivity(EventActivity::class.java)
                })
            } else
                this@LoginActivity extToast resources.getString(R.string.no_user)

        }
    }
}
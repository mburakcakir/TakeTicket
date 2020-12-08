package com.mburakcakir.taketicket.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.activity.HomeActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
import kotlinx.android.synthetic.main.activity_register.edtPassword
import kotlinx.android.synthetic.main.activity_register.edtUsername
import kotlinx.android.synthetic.main.activity_welcome.btnLogin

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()
            val checkUser = loginViewModel.checkIfUserExists(username, password)
            if (checkUser) {
                loginViewModel.getUserByUsername(username, password).observe(this, {
                    loginViewModel.startSession(it)
                   Log.d("data",it.toString())
                    extOpenActivity(HomeActivity::class.java)
                    finish()
                })
            } else
                this@LoginActivity extToast resources.getString(R.string.no_user)

        }
    }
}
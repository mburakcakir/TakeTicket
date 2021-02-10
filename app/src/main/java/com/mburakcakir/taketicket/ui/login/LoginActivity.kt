package com.mburakcakir.taketicket.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
            loginViewModel.login(username, password)
        }

        edtUsername.afterTextChanged {
            loginViewModel.loginDataChanged(
                edtUsername.text.toString(),
                edtPassword.text.toString()
            )
        }

        edtPassword.afterTextChanged {
            loginViewModel.loginDataChanged(
                edtUsername.text.toString(),
                edtPassword.text.toString()
            )
        }

        loginViewModel.loginFormState.observe(this, {
            btnLogin.isEnabled = it.isDataValid
            if (it.passwordError != null)
                edtUsername.error = getString(it.passwordError)
            if (it.usernameError != null)
                edtUsername.error = getString(it.usernameError)
        })

        loginViewModel.loginResult.observe(this, {
            if (it.success != null) {
                finish()
                extOpenActivity(EventActivity::class.java)
            }
            if (it.error != null)
                this@LoginActivity extToast getString(R.string.no_user)
        })

    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
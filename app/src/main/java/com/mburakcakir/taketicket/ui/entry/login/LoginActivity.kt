package com.mburakcakir.taketicket.ui.entry.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.activity.EventActivity
import com.mburakcakir.taketicket.ui.entry.register.RegisterActivity
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
            dataChanged()
        }

        edtPassword.afterTextChanged {
            dataChanged()
        }

        loginViewModel.entryFormState.observe(this, {
            btnLogin.isEnabled = it.isDataValid
            if (it.passwordError != null)
                edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                edtUsername.error = getString(it.usernameError)
        })

        loginViewModel.entryResultState.observe(this, {
            if (it.success != null) {
                finish()
                extOpenActivity(EventActivity::class.java)
            }
            if (it.error != null)
                this@LoginActivity extToast getString(R.string.no_user)
        })

    }

    fun dataChanged() {
        loginViewModel.loginDataChanged(
            edtUsername.text.toString(),
            edtPassword.text.toString()
        )
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
package com.mburakcakir.taketicket.ui.entry.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.ui.entry.login.LoginActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        init()
    }

    fun init() {

        btnRegister.setOnClickListener {
            val userModel = UserModel(
                edtName.text.toString(),
                edtUsername.text.toString(),
                edtMail.text.toString(),
                edtPassword.text.toString()
            )
            registerViewModel.insertUser(userModel)
        }

        registerViewModel.entryFormState.observe(this, {
            btnRegister.isEnabled = it.isDataValid

            if (it.passwordError != null)
                edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                edtUsername.error = getString(it.usernameError)
        })

        registerViewModel.entryResultState.observe(this, {
            if (it.error != null)
                this@RegisterActivity extToast it.error
            if (it.success != null) {
                this@RegisterActivity extToast it.success
                finish()
                extOpenActivity(LoginActivity::class.java)
            }
        })
    }
}
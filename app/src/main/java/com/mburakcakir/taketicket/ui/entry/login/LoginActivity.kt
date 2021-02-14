package com.mburakcakir.taketicket.ui.entry.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.ActivityLoginBinding
import com.mburakcakir.taketicket.ui.activity.EventActivity
import com.mburakcakir.taketicket.ui.entry.register.RegisterActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            extOpenActivity(RegisterActivity::class.java)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            loginViewModel.login(username, password)
        }

        binding.edtUsername.afterTextChanged {
            dataChanged()
        }

        binding.edtPassword.afterTextChanged {
            dataChanged()
        }

        loginViewModel.entryFormState.observe(this, {
            binding.btnLogin.isEnabled = it.isDataValid
            if (it.passwordError != null)
                binding.edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                binding.edtUsername.error = getString(it.usernameError)
        })

        loginViewModel.result.observe(this, {
            if (it.success != null) {
                finish()
                extOpenActivity(EventActivity::class.java)
            }
            if (it.error != null)
                this@LoginActivity extToast it.error
            if (it.warning != null)
                this@LoginActivity extToast it.warning
            if (it.loading != null)
                this@LoginActivity extToast it.loading
        })

    }

    fun dataChanged() {
        loginViewModel.loginDataChanged(
            binding.edtUsername.text.toString(),
            binding.edtPassword.text.toString()
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
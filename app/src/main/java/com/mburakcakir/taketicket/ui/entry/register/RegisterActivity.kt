package com.mburakcakir.taketicket.ui.entry.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.databinding.ActivityRegisterBinding
import com.mburakcakir.taketicket.ui.entry.login.LoginActivity
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
class RegisterActivity : AppCompatActivity() {
    lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            val userModel = UserModel(
                binding.edtName.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtMail.text.toString(),
                binding.edtPassword.text.toString()
            )
            registerViewModel.insertUser(userModel)
        }

        registerViewModel.entryFormState.observe(this, {
            binding.btnRegister.isEnabled = it.isDataValid

            if (it.passwordError != null)
                binding.edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                binding.edtUsername.error = getString(it.usernameError)
        })

        registerViewModel.result.observe(this, {
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
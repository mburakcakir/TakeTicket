package com.mburakcakir.taketicket.ui.register

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.ui.login.LoginActivity
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
            val username = userModel.userName
            val password = userModel.password

            val checkUser = registerViewModel.checkIfUserExists(username, password)
            Log.d("checkUser",checkUser.toString())
            if(checkUser)
                this@RegisterActivity extToast resources.getString(R.string.already_registered)
            else {
                registerViewModel.insertUser(userModel)
                extOpenActivity(LoginActivity::class.java)
                finish()
            }
//            registerViewModel.getUserByUsername(username, password).observe(this, {
//                registerViewModel.startSession(it)
//                Log.d("data",it.toString())
//                extOpenActivity(HomeActivity::class.java)
//                finish()
//            })
        }
    }

}
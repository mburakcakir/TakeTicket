package com.mburakcakir.taketicket.data.repository.user

import android.content.Context
import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.entity.UserModel

interface UserRepository {
    fun checkIfUserExists(userName: String, password: String) : Boolean
    suspend fun insertUser(userModel: UserModel)
    fun getUserByUsername(username : String, password : String) : LiveData<UserModel>
    fun startSession(userModel: UserModel, context: Context)
}
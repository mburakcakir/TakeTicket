package com.mburakcakir.taketicket.data.repository.user

import android.content.Context
import com.mburakcakir.taketicket.data.db.entity.UserModel

interface UserRepository {
    fun checkIfUserExists(userName: String, password: String) : Boolean
    suspend fun insertUser(userModel: UserModel)
    fun getUserByUsername(username: String, password: String): UserModel
    fun startSession(userModel: UserModel, context: Context)
}
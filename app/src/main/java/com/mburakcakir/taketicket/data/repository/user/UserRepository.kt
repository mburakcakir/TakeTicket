package com.mburakcakir.taketicket.data.repository.user

import android.content.Context
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun checkIfUserExists(userName: String, password: String): Boolean
    suspend fun insertUser(userModel: UserModel): Flow<Resource<Boolean>>
    fun getUserByUsername(username: String, password: String): Flow<Resource<UserModel>>
    fun startSession(userModel: UserModel, context: Context)
}
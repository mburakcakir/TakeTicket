package com.mburakcakir.taketicket.data.repository.user

import android.content.Context
import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.dao.UserDao
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.utils.SessionManager

class UserRepositoryImpl(private val userDao : UserDao) : UserRepository {
    lateinit var sessionManager: SessionManager

    override fun checkIfUserExists(userName : String, password: String) = userDao.checkIfUserExists(userName, password)!= 0
    override suspend fun insertUser(userModel : UserModel) = userDao.insertUser(userModel)
    override fun getUserByUsername(username : String, password : String) : LiveData<UserModel> = userDao.getUserByUsername(username, password)

    override fun startSession(userModel: UserModel, context: Context) {
        sessionManager = SessionManager(context)
        sessionManager.startSession(userModel)
    }
}
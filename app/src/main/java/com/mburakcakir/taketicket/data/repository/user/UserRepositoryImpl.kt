package com.mburakcakir.taketicket.data.repository.user

import android.content.Context
import com.mburakcakir.taketicket.data.db.dao.UserDao
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.utils.Resource
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    lateinit var sessionManager: SessionManager

    override fun getUserByUsername(username: String, password: String) = flow {
        emit(Resource.Loading())
        try {
            if (checkIfUserExists(username, password))
                emit(Resource.Success(userDao.getUserByUsername(username, password)))
            else
                emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

    override fun startSession(userModel: UserModel, context: Context) {
        sessionManager = SessionManager(context).apply {
            startSession(userModel)
        }
    }

    override fun checkIfUserExists(userName: String, password: String) =
        userDao.checkIfUserExists(userName, password) != 0

    override suspend fun insertUser(userModel: UserModel) = flow {
        emit(Resource.Loading())
        try {
            if (checkIfUserExists(userModel.userName, userModel.password))
                emit(Resource.Success(false))
            else {
                emit(Resource.Success(true))
                userDao.insertUser(userModel)
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

}
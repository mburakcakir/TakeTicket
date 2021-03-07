package com.mburakcakir.taketicket.ui.entry.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.ui.entry.EntryViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.Status
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : EntryViewModel(application) {
    private val userRepository: UserRepository

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    private fun startSession(userModel: UserModel) {
        sessionManager.startSession(userModel)
    }

    fun login2(username: String, password: String) = viewModelScope.launch {
        userRepository.getUserByUsername(username, password).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> _result.postValue(Result(loading = "Giriş Yapılıyor..."))
                    Status.SUCCESS -> {
                        if (it.data?.userName.isNullOrEmpty())
                            _result.postValue(Result(warning = "Kullanıcı Kayıtlı Değil"))
                        else {
                            startSession(it.data!!)
                            _result.postValue(Result(success = "Giriş Başarılı"))
                        }
                    }
                    Status.ERROR -> _result.postValue(
                        Result(
                            error = "Giriş Başarısız."
                        )
                    )
                }
            }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        userRepository.getUserByUsername(username, password)
            .onStart { _result.postValue(Result(loading = "Giriş Yapılıyor...")) }
            .catch {
                _result.postValue(Result(error = it.message))
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> checkLogin(it.data)
                    Status.ERROR -> _result.postValue(Result(error = "Giriş Başarısız."))
                }
            }
    }

    private fun checkLogin(userModel: UserModel?) {
        if (userModel == null)
            _result.postValue(Result(warning = "Kullanıcı Kayıtlı Değil"))
        else {
            startSession(userModel)
            _result.postValue(Result(success = "Giriş Başarılı"))
        }
    }


}
package com.mburakcakir.taketicket.ui.entry.register

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.ui.entry.EntryViewModel
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application
) : EntryViewModel(application) {
    private val sessionManager: SessionManager
    private val userRepository: UserRepository

    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun insertUser(userModel: UserModel) = viewModelScope.launch {
        userRepository.insertUser(userModel).collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> _result.value = Result(loading = "Kayıt Oluşturuluyor")
                    Status.SUCCESS -> {
                        _result.value =
                            if (it.data!!) Result(success = "Kayıt Başarılı")
                            else Result(error = "Kullanıcı Kayıtlı")
                    }
                    Status.ERROR -> _result.value = Result(
                        error = "Kullanıcı kaydında bir hata oluştu."
                    )
                }
            }
        }
    }
}

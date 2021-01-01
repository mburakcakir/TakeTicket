package com.mburakcakir.taketicket.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager

class LoginViewModel (
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val userRepository: UserRepository

    // burada 25. satırda SessionManager class'ı, 27. satırda UserRepositoryImpl class'ı ve nesneleri tanımlanmaktadır.
    // init bloğu dışında da bu nesneler kullanılarak fonksiyonlar tanımlanmakta, Activity üzerinden de fonksiyonlar çağırılmaktadır.
    // UserRepositoryImpl nesnesi için UserDao nesnesi, UserDao nesnesi için TicketDatabase nesnesi gerekmektedir.
    // Bu işlemler LoginViewModel classında oluşturularak Activity'nin, nesnelerin oluşturulmasından haberi olmadan veya hangi nesnelere erişmesi gerektiği belirtilmeden
    // verilere ulaşması sağlanmakta, aynı zamanda View-Model arasındaki bağlantıyı Repository aracılığıyla kurulmaktadır.
    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun startSession(userModel: UserModel) = sessionManager.startSession(userModel)
    fun checkIfUserExists(username: String, password: String) =
        userRepository.checkIfUserExists(username, password)

    fun getUserByUsername(username: String, password: String): LiveData<UserModel> =
        userRepository.getUserByUsername(username, password)

}
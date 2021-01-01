package com.mburakcakir.taketicket.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// FACADE PATTERN
class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {
    val sessionManager: SessionManager
    val userRepository: UserRepository

    // burada 28. satırda SessionManager class'ı, 30. satırda UserRepositoryImpl class'ı ve nesneleri tanımlanmaktadır.
    // init bloğu dışında da bu nesneler kullanılarak fonksiyonlar tanımlanmakta, Activity üzerinden de fonksiyonlar çağırılmaktadır.
    // UserRepositoryImpl nesnesi için UserDao nesnesi, UserDao nesnesi için TicketDatabase nesnesi gerekmektedir.
    // Bu işlemler RegisterViewModel classında oluşturularak Activity'nin, nesnelerin oluşturulmasından haberi olmadan veya hangi nesnelere erişmesi gerektiği belirtilmeden
    // verilere ulaşması sağlanmakta, aynı zamanda View-Model arasındaki bağlantıyı Repository aracılığıyla kurulmaktadır.
    init {
        sessionManager = SessionManager(application)
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
    }

    fun insertUser(userModel: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insertUser(userModel)
    }

    fun getUserByUsername(username : String, password : String) : LiveData<UserModel> = userRepository.getUserByUsername(username, password)
    fun checkIfUserExists(username : String, password: String) = userRepository.checkIfUserExists(username, password)

    //fun startSession(userModel: UserModel) = sessionManager.startSession(userModel)
    fun startSession(userModel: UserModel) = userRepository.startSession(
        userModel,
        getApplication()
    )


}

package com.mburakcakir.taketicket.ui.entry.register

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.data.repository.user.UserRepository
import com.mburakcakir.taketicket.data.repository.user.UserRepositoryImpl
import com.mburakcakir.taketicket.ui.entry.EntryViewModel
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application
) : EntryViewModel(application) {
    private val userRepository: UserRepository
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        userRepository = UserRepositoryImpl(database.userDao())
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }

    fun uploadFile(fileName: String, filePath: Uri?): String {
        filePath?.let {
            val imageRef = storageReference!!.child(fileName)
            imageRef.putFile(filePath)
                .addOnSuccessListener {
                    Log.v("image", "Görsel Yüklendi")
                }
                .addOnFailureListener {
                    Result(error = "Görsel Yüklenemedi")
                }
                .addOnProgressListener {

                }
        }
        return filePath.toString()
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

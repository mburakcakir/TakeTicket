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
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.Status
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
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

    fun insertUser(userModel: UserModel) = viewModelScope.launch {
        userRepository.insertUser(userModel)
            .onStart { _result.value = Result(loading = "Kayıt Oluşturuluyor") }
            .catch {
                _result.value = Result(
                    error = "Kullanıcı kaydında bir hata oluştu."
                )
            }
            .collect {
                when (it.status) {
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

    fun uploadFile(fileName: String, filePath: Uri?): String {
        filePath?.let {
            val imageRef = storageReference!!.child(fileName)

            imageRef.putFile(filePath)
                .addOnSuccessListener {
                    successListener(imageRef)
                }
                .addOnFailureListener {
                    Result(error = "Görsel Yüklenemedi")
                }
                .addOnProgressListener {

                }
        }
        return filePath.toString()
    }

    private fun successListener(imageRef: StorageReference) {
        imageRef.downloadUrl
            .addOnSuccessListener {
                saveImage(it)
                Log.v("baseImageUri", it.toString())
            }.addOnFailureListener {
                Log.v("baseImageUri", "ErrorImage")
            }
    }

    private fun saveImage(uri: Uri) {
        sessionManager.saveImageUri(uri.toString())
        userRepository.setUserImageUri(
            uri.toString(),
            sessionManager.getUsername()
        )
    }
}

package com.mburakcakir.taketicket.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.SessionManager

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result
    val sessionManager: SessionManager

    init {
        sessionManager = SessionManager(application)
    }

    fun loadImage(username: String) {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference

        storageReference.child(username).downloadUrl.addOnSuccessListener {
            sessionManager.saveImageUri(it.toString())
            Log.v("baseImageUri", it.toString())
        }.addOnFailureListener {
            Log.v("baseImageUri", "ErrorImage")
        }
    }
}
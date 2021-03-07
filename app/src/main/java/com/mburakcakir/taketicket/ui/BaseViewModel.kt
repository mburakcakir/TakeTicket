package com.mburakcakir.taketicket.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.SessionManager

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result
    val sessionManager: SessionManager
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    init {
        sessionManager = SessionManager(application)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
    }
}
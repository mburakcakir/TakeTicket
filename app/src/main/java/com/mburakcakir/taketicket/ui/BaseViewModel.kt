package com.mburakcakir.taketicket.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mburakcakir.taketicket.utils.Result

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result
}
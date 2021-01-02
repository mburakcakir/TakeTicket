package com.mburakcakir.taketicket.ui.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InfoViewModel(
    private val infoRepository: InfoRepository
) : ViewModel() {

    val allInfo: MutableLiveData<List<InfoModel>> by lazy {
        MutableLiveData<List<InfoModel>>()
    }

    fun getAllInfo() = viewModelScope.launch {
        infoRepository.getAllInfo().collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> Log.v("INFOLOADING", "LOADING")
                    Status.SUCCESS -> allInfo.value = it.data
                    Status.ERROR -> Log.v("INFOERROR", "ERROR")
                }
            }
        }
    }
}
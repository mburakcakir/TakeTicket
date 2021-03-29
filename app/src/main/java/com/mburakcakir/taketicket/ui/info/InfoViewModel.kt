package com.mburakcakir.taketicket.ui.info

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.taketicket.data.repository.info.InfoRepositoryImpl
import com.mburakcakir.taketicket.network.model.info.InfoModel
import com.mburakcakir.taketicket.network.service.ServiceApiClient
import com.mburakcakir.taketicket.network.service.ServiceProvider
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class InfoViewModel(
    application: Application
) : BaseViewModel(application) {

    private val _allInfo = MutableLiveData<List<InfoModel>>()
    val allInfo: LiveData<List<InfoModel>> = _allInfo
    private val infoRepository: InfoRepository
    private val serviceClient: ServiceApiClient

    init {
        serviceClient = ServiceProvider().getServiceApi()
        infoRepository = InfoRepositoryImpl(serviceClient)
        getAllInfo()
    }

    private fun getAllInfo() = viewModelScope.launch {
        infoRepository.getAllInfo()
            .onStart {
                _result.value =
                    Result(loading = "Geliştirici Bilgileri Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _allInfo.value = it.data!!
                        _result.value = Result("Bilgiler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }
}
package com.mburakcakir.taketicket.ui.info

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.network.model.InfoModel
import com.mburakcakir.taketicket.data.network.service.ServiceApiClient
import com.mburakcakir.taketicket.data.network.service.ServiceProvider
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.taketicket.data.repository.info.InfoRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.utils.Result
import com.mburakcakir.taketicket.utils.Status
import kotlinx.coroutines.flow.collect
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
        infoRepository.getAllInfo().collect {
            it.let {
                when (it.status) {
                    Status.LOADING -> _result.value =
                        Result(loading = "Geliştirici Bilgileri Yükleniyor..")
                    Status.SUCCESS -> {
                        _allInfo.value = it.data!!
                        _result.value = Result("Bilgiler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(loading = "Bir hata oluştu.")
                }
            }
        }
    }
}
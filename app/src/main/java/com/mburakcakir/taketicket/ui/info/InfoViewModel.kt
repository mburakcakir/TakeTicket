package com.mburakcakir.taketicket.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.vbtinternshipschedule.utils.Resource
import kotlinx.coroutines.Dispatchers

class InfoViewModel(
    private val repository: InfoRepository
) : ViewModel() {

    fun getInfo() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getSchedule()))
        } catch (exception: Exception) {
            emit(Resource.error("Restart app and try again."))
        }
    }
}
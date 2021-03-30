package com.mburakcakir.taketicket.ui.event.foreign.event

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.remote.model.event.ResponseEvents
import com.mburakcakir.taketicket.data.repository.event.EventRepository
import com.mburakcakir.taketicket.data.repository.event.EventRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ForeignEventViewModel(
    application: Application
) : BaseViewModel(application) {
    private val eventRepository: EventRepository
    private val _foreignEvents = MutableLiveData<ResponseEvents>()
    val foreignEvents: LiveData<ResponseEvents> = _foreignEvents

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        eventRepository = EventRepositoryImpl(database.eventDao())
        getForeignEvents()
    }

    private fun getForeignEvents() = viewModelScope.launch {
        eventRepository.getForeignEvents()
            .onStart {
                _result.value =
                    Result(loading = "Yabancı Etkinlikler Yükleniyor..")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _foreignEvents.value = it.data!!
                        _result.value = Result("Yabancı Etkinlikler Yüklendi.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bir hata oluştu.")
                }
            }
    }
}
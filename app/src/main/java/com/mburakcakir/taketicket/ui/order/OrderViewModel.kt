package com.mburakcakir.taketicket.ui.order

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mburakcakir.taketicket.data.db.TicketDatabase
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepository
import com.mburakcakir.taketicket.data.repository.ticket.TicketRepositoryImpl
import com.mburakcakir.taketicket.ui.BaseViewModel
import com.mburakcakir.taketicket.util.Result
import com.mburakcakir.taketicket.util.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : BaseViewModel(application) {

    private val ticketRepository: TicketRepository

    private val _countTicket = MutableLiveData<Int>(1)
    val countTicket: LiveData<Int> = _countTicket

    private val _countPrice = MutableLiveData<Int>(0)
    val countPrice: LiveData<Int> = _countPrice

    private var basePrice: Int = 0

    init {
        val database = TicketDatabase.getDatabase(application, viewModelScope)
        ticketRepository = TicketRepositoryImpl(database.ticketDao())
    }

    fun setCountPrice(price: Int) {
        basePrice = price
        _countPrice.value = price
    }

    private fun updateCountPrice() {
        _countPrice.value = basePrice.times(_countTicket.value!!)
    }

    fun updateCountTicket(count: Int) {
        _countTicket.value = _countTicket.value?.plus(count)
        updateCountPrice()
    }

    fun insertTicket(ticketCount: Int, ticketModel: TicketModel) = viewModelScope.launch {
        val copiedTicketModel = ticketModel.copy(
            ticketCount = ticketCount
        )
        ticketRepository.insertTicket(copiedTicketModel)
            .onStart {
                _result.value = Result(loading = "Bilet Kaydediliyor...")
            }
            .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _result.value =
                            if (it.data!!) Result(success = "Bilet Kaydedildi.") else Result(warning = "Bilet Zaten Alınmış.")
                    }
                    Status.ERROR -> _result.value = Result(error = "Bilet Kaydedilemedi.")
                }
            }
    }
}
package com.mburakcakir.taketicket.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.data.repository.info.InfoRepository

@Suppress("UNCHECKED_CAST")
class InfoViewModelFactory(
    private val repository: InfoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoViewModel(repository) as T
    }
}

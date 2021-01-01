package com.mburakcakir.taketicket.ui.info

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.network.service.ServiceProvider
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.taketicket.data.repository.info.InfoRepositoryImpl
import com.mburakcakir.vbtinternshipschedule.utils.Status
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    private lateinit var viewModel: InfoViewModel
    private val serviceClient by lazy {
        ServiceProvider().getServiceApi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        toolbarInfo.title = getString(R.string.contact)
        setSupportActionBar(toolbarInfo)
        init()
    }

    private fun init() {

        val repository: InfoRepository = InfoRepositoryImpl(serviceClient)
        val mainActivityViewModelFactory = InfoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, mainActivityViewModelFactory).get(
            InfoViewModel::class.java
        )

        //Adapter çağırıldığı yer 1
        recyclerView.adapter = InfoAdapter()

        getAllSchedules()
    }

    //Adapter çağırıldığı yer 2
    // Observer Pattern, info her değiştiğinde observe metodu tetiklenecek ve adapter güncellenecek.
    // Böylece veri değişimini elle kontrol etmemize gerek kalmayacak. Hem güncel veriyi alacağız hem de set işlemlerini yapacağız.
    private fun getAllSchedules() {
        viewModel.getInfo().observe(this, {
            it?.let { apiResult ->
                when (apiResult.status) {
                    Status.SUCCESS -> {
                        Log.v("LOGSUCCESS", it.data.toString())
                        //Submit with SubmitList by ListAdapter to adapter when change data
                        (recyclerView.adapter as InfoAdapter).submitList(it.data)
                    }
                    Status.ERROR -> {
                        Log.v("LOGERROR ", it.message.toString())
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }
                    Status.LOADING -> {
                        Log.v("LOGLOADING ", it.data.toString())
                    }
                }
            }
        })
    }
}
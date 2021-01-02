package com.mburakcakir.taketicket.ui.info

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.network.service.ServiceProvider
import com.mburakcakir.taketicket.data.repository.info.InfoRepositoryImpl
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

        val repository = InfoRepositoryImpl(serviceClient)
        val mainActivityViewModelFactory = InfoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, mainActivityViewModelFactory).get(
            InfoViewModel::class.java
        )

        rvInfo.adapter = InfoAdapter()
        rvInfo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.getAllInfo()

        viewModel.allInfo.observe(this, {
            it?.let {
                (rvInfo.adapter as InfoAdapter).submitList(it)
                Log.d("tag2", it.toString())
            }
        })
    }
}
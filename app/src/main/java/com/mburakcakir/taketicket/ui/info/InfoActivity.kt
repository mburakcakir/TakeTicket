package com.mburakcakir.taketicket.ui.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.repository.info.InfoRepository
import com.mburakcakir.taketicket.data.repository.info.InfoRepositoryImpl
import com.mburakcakir.taketicket.data.network.service.ServiceProvider
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
        toolbarInfo.setTitle(resources.getString(R.string.contact))
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
        recyclerView.apply {
            adapter = InfoAdapter()
        }

        getAllSchedules()
    }
    //Adapter çağırıldığı yer 2
    private fun getAllSchedules() {
        viewModel.getInfo().observe(this, {
            it?.let { apiResult ->
                when (apiResult.status) {
                    Status.SUCCESS -> {
                        Log.v("LOG SUCCESS", it.data.toString())
                        //Submit with SubmitList by ListAdapter to adapter when change data
                        (recyclerView.adapter as InfoAdapter).submitList(it.data)
                    }
                    Status.ERROR -> {
                        Log.v("LOG ERROR ", it.message.toString())
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }
                    Status.LOADING -> {
                        Log.v("LOG LOADING ", it.data.toString())
                    }
                }
            }
        })
    }
}
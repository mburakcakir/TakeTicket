package com.mburakcakir.taketicket.ui.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.ActivityInfoBinding
import com.mburakcakir.taketicket.utils.extToast

class InfoActivity : AppCompatActivity() {
    private lateinit var infoViewModel: InfoViewModel
    private lateinit var binding: ActivityInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        init()
    }

    private fun init() {
        binding.toolbarInfo.title = getString(R.string.contact)
        setSupportActionBar(binding.toolbarInfo)

        binding.rvInfo.adapter = InfoAdapter()
        binding.rvInfo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        infoViewModel.result.observe(this, {
            if (it.success != null)
                this@InfoActivity extToast it.success
            if (it.error != null)
                this@InfoActivity extToast it.error
            if (it.loading != null)
                this@InfoActivity extToast it.loading
        })

        infoViewModel.allInfo.observe(this, {
            (binding.rvInfo.adapter as InfoAdapter).submitList(it)
        })

//        viewModel.allInfo.observe(this, {
//            it?.let {
//                (binding.rvInfo.adapter as InfoAdapter).submitList(it)
//                Log.d("tag2", it.toString())
//            }
//        })
    }
}
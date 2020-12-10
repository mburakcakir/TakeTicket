package com.mburakcakir.taketicket.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.ui.info.InfoActivity
import com.mburakcakir.taketicket.ui.ticket.TicketActivity
import com.mburakcakir.taketicket.ui.viewmodel.HomeViewModel
import com.mburakcakir.taketicket.utils.extOpenActivity
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.showDetailDialog
import com.mburakcakir.vbtinternshipschedule.ui.adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        toolbar.title = "Hoşgeldin ${homeViewModel.sessionManager.getUsername()}"
        setSupportActionBar(toolbar)

        //Adapter çağırıldığı yer 1
        recyclerView.apply {
            adapter = ProductAdapter {
                this@HomeActivity.showDetailDialog(it, homeViewModel)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Adapter çağırıldığı yer 2
        homeViewModel.allProducts.observe(this, { allProducts ->
            allProducts?.let {
                (recyclerView.adapter as ProductAdapter).submitList(allProducts)
                Log.d("tag2", allProducts.toString())
            }
        })

    }

    override fun onBackPressed() {
        if (backPressedTime + 5000 > System.currentTimeMillis()) {
            //  homeViewModel.sessionManager.endSession()
            this extToast resources.getString(R.string.login_again)
            finish()
        } else {
            Toast.makeText(baseContext, resources.getString(R.string.exit_app), Toast.LENGTH_SHORT)
                .show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> this@HomeActivity extOpenActivity TicketActivity::class.java
            R.id.action_info -> this@HomeActivity extOpenActivity InfoActivity::class.java
        }
        return super.onOptionsItemSelected(item)
    }

}

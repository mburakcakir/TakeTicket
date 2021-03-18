package com.mburakcakir.taketicket.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mburakcakir.taketicket.data.db.entity.TicketModel
import com.mburakcakir.taketicket.ui.event.foreign.ForeignEventFragment
import com.mburakcakir.taketicket.ui.event.turkish.TurkishEventFragment

class HomeEventPagerAdapter(fragment: Fragment, private val onClickEvent: (TicketModel) -> Unit) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        if (position == 0) TurkishEventFragment().apply {
            this.setEventOnClickListener(onClickEvent)
        }
        else ForeignEventFragment()
}
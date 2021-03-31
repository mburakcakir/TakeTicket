package com.mburakcakir.taketicket.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mburakcakir.taketicket.data.db.entity.EventModel
import com.mburakcakir.taketicket.ui.event.foreign.event.ForeignEventFragment
import com.mburakcakir.taketicket.ui.event.foreign.movie.ForeignMovieFragment
import com.mburakcakir.taketicket.ui.event.turkish.TurkishEventFragment

class HomeEventPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private lateinit var eventClickListener: (EventModel) -> Unit

    fun setOnEventClickListener(eventClickListener: (EventModel) -> Unit) {
        this.eventClickListener = eventClickListener
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> TurkishEventFragment().apply {
                setOnEventClickListener(eventClickListener)
            }
            1 -> ForeignEventFragment().apply {
                setOnEventClickListener(eventClickListener)
            }
            2 -> ForeignMovieFragment()
            else -> ForeignMovieFragment()
        }
}
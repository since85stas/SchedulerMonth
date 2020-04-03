package stas.batura.schedulermonth.ui.calendar

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class PeriodsCalendarAdapter {

    private class PeriodsViewHolder(val view: View) : RecyclerView.ViewHolder (view), LayoutContainer  {



        override val containerView: View?
            get() = view
    }

}
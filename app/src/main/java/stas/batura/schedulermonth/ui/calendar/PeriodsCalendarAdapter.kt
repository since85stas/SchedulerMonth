package stas.batura.schedulermonth.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.periods_calendar_item.view.*
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.repository.room.Period
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PeriodsCalendarAdapter ( val periods :List<Period>, val viewModel: CalendarFragmentViewModel) :
    RecyclerView.Adapter<PeriodsCalendarAdapter.PeriodsViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.periods_calendar_item,
            parent, false)
        return PeriodsViewHolder(view, viewModel)
    }

    override fun getItemCount(): Int {
        return periods.size
    }

    override fun onBindViewHolder(holder: PeriodsViewHolder, position: Int) {
        holder.bind(periods.get(position))
    }

    class PeriodsViewHolder(val view: View, val viewModel: CalendarFragmentViewModel) : RecyclerView.ViewHolder (view), LayoutContainer  {

        fun bind ( item : Period ) {
            view.period_id.text = item.periodId.toString()

            val periodStartDate: Date = Date(item.periodStartDate)
            val df: DateFormat = SimpleDateFormat("yyyy.MM.dd")
            view.period_start_date.text = df.format(periodStartDate)

            val periodEndDate: Date = Date(item.periodStartDate)
            val dfEnd: DateFormat = SimpleDateFormat("yyyy.MM.dd")
            view.period_end_date.text = dfEnd.format(periodEndDate)

            view.setOnClickListener {
                viewModel.repository.saveMainDataPeriod(
                    item.periodId
                )
            }
        }

        override val containerView: View?
            get() = view
    }

}
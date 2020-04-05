package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.calendar_fragment.*
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.databinding.CalendarFragmentBinding
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class CalendarFragment : Fragment() {

    lateinit var viewModel: CalendarFragmentViewModel

    lateinit var viewManager : RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val bindings : CalendarFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.calendar_fragment, container, false)

        val application: Application = requireNotNull(this.activity).application
        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao

        val calendarFragmentArgs by navArgs<CalendarFragmentArgs>()
        val sectionId = calendarFragmentArgs.sectionIdToOpen
        val viewModelFactory = CalendarFragmentViewModelFactory(database, application, sectionId)

        // get a view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CalendarFragmentViewModel::class.java)

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val events: MutableList<EventDay> = ArrayList()
//
//        val calendar = Calendar.getInstance()
//        events.add(EventDay(calendar, R.drawable.ic_arrow_right))
//        material_calendar.setEvents(events)

        val calendars: MutableList<Calendar> = ArrayList()
        var calen = Calendar.getInstance()
        calen.set(2020,4,10)

        material_calendar.setDate(calen)

        calendars.add(calen)
        material_calendar.setHighlightedDays(calendars)

        // наблюдаем за списком уроков
        viewModel.lessonsList.observe(viewLifecycleOwner, Observer {
            for (l in it) {
                val set : MutableSet<Long> = HashSet<Long>();
                set.add(System.currentTimeMillis() + 30*60*60*1000)
                set.add(System.currentTimeMillis() + 80*60*60*1000)
                //Define colors
                //Define colors

            }
            print("lessons loading")
        })

        // наблюдаем за списком периодов
        viewModel.periodList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val adapter = PeriodsCalendarAdapter(it, viewModel)
                periods_recycle_view.adapter = adapter
            }
        })

        viewManager = LinearLayoutManager(parentFragment!!.requireContext())
        periods_recycle_view.apply {
            layoutManager = viewManager
        }

        // наблюдаем за изменением основной даты
        viewModel.mainData.observe(viewLifecycleOwner, Observer {

            print("main")
        })
    }
}
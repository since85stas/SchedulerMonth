package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.databinding.CalendarFragmentBinding
import stas.batura.schedulermonth.databinding.CreateSectionFragmentBinding
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.ui.create.CreateSectionViewModel
import stas.batura.schedulermonth.ui.create.CreateSectionViewModelFactory
import stas.batura.schedulermonth.ui.create.SectionCreateFragmnetDirections

class CalendarFragment : Fragment() {

    lateinit var viewModel: CalendarFragmentViewModel

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
        val viewModelFactory = CalendarFragmentViewModelFactory(database, application)

        // get a view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CalendarFragmentViewModel::class.java)


        return bindings.root
    }

}
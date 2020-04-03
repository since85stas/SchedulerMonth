package stas.batura.schedulermonth.ui.section

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.section_fragment.*
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.databinding.SectionFragmentBinding
import stas.batura.schedulermonth.repository.room.LessonsDatabase

class SectionFragment : Fragment() {

    // view model
    lateinit var viewModel : SectionViewModel

    lateinit var viewManager : RecyclerView.LayoutManager

    /**
     *  creating view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sectionFragmentArgs by navArgs<SectionFragmentArgs>()

        // id of opened section
        val openedId = sectionFragmentArgs.sectionIdtoOpen

        // Get a reference to the binding object and inflate the fragment views.
        val bindings : SectionFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.section_fragment, container,false)

        val application : Application = requireNotNull(this.activity).application
        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao
        val viewModelFactory = SectionViewModelFactory( database, application, openedId)

        // get a view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get( SectionViewModel :: class.java)

        // связываем переменные в модели и ui
        bindings.viewModel = viewModel



        return bindings.root
    }

    /**
     * populating view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sectionLive.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                section_name.text = it.sectionName

                // получаем доп информацию о секции
                viewModel.getSectionInfo(it)
            } else {
                print("smth wrong with section getting from DB")
            }
        })

        // наблюдаем за списком уроков
        viewModel.lessonslive.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val adapter = LessonsAdapter(it)
                section_lessons_current_period.adapter = adapter

                // check if all lessons done
                if ( adapter.isFull() ) {
                    val toast = Toast.makeText(requireNotNull(this.activity),
                        "All lessons complete",
                        Toast.LENGTH_LONG);
                    toast.show()

                    // creating new period
                    viewModel.createNewPeriod()
                }
                print ("lessons")
            } else {
                print ("wrong")
            }
        })

        // навигация в календарь
        viewModel.navToCalendar.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action = SectionFragmentDirections.actionNavSectionToNavCalendar(it)
                this.findNavController().navigate(action)

                viewModel.navToCalendarfinish()
            }
        })

        viewManager = LinearLayoutManager(parentFragment!!.requireContext())
        section_lessons_current_period.apply {
            layoutManager = viewManager
        }
    }
}
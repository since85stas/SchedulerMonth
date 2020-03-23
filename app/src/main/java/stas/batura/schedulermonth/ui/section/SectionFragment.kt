package stas.batura.schedulermonth.ui.section

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.databinding.CreateSectionFragmentBinding
import stas.batura.schedulermonth.databinding.SectionFragmentBinding
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.ui.create.CreateSectionViewModel
import stas.batura.schedulermonth.ui.create.CreateSectionViewModelFactory
import stas.batura.schedulermonth.ui.create.SectionCreateFragmnetDirections

class SectionFragment : Fragment() {

    // view model
    lateinit var viewModel : SectionViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sectionFragmentArgs by navArgs<SectionFragmentArgs>()

        val openedId = sectionFragmentArgs.sectionIdtoOpen

//         Get a reference to the binding object and inflate the fragment views.
        val bindings : SectionFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.section_fragment,container,false)

        val application : Application = requireNotNull(this.activity).application
        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao
        val viewModelFactory = CreateSectionViewModelFactory( database, application)

        // get a view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get( SectionViewModel :: class.java)

        // связываем переменные в модели и ui
        bindings.viewModel = viewModel

//        viewModel.openHomeFragment.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                viewModel.navigateToHomeFinish()
//                this.findNavController().navigate(SectionCreateFragmnetDirections.openHome())
//            }
//        })

        return bindings.root
    }
}
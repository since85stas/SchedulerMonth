package stas.batura.schedulermonth.ui.create

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.databinding.CreateSectionFragmentBinding
import stas.batura.schedulermonth.repository.room.LessonsDatabase

class SectionCreateFragmnet : Fragment () {

    lateinit var viewModel : CreateSectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val bindings: CreateSectionFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.create_section_fragment,container,false)

        val application : Application = requireNotNull(this.activity).application
        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao
        val viewModelFactory = CreateSectionViewModelFactory( database, application)

        // get a view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get( CreateSectionViewModel :: class.java)

        // связываем переменные в модели и ui
        bindings.viewModel = viewModel

        // открываем домашний фрагмент
        viewModel.openHomeFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.navigateToHomeFinish()
//                this.findNavController().navigate(SectionCreateFragmnetDirections.openHome())
                val test = -1;
                val action = SectionCreateFragmnetDirections.openHome()
                this.findNavController().navigate(action)
            }
        })

        viewModel.openSectionFragment.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.navigateToSectionFinish()
                val action = SectionCreateFragmnetDirections.openCreatedSection(it)
                this.findNavController().navigate(action)
            }
        })


        return bindings.root
    }
}
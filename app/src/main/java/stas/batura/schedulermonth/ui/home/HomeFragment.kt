package stas.batura.schedulermonth.ui.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.databinding.FragmentHomeBinding
import stas.batura.schedulermonth.ui.dialogs.CreateSectionDialog

class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val bindings: FragmentHomeBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home,container,false)

        val application : Application = requireNotNull(this.activity).application
        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao
        val viewModelFactory = HomeViewModelFactory( database, application)

        // get a view model
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get( HomeViewModel :: class.java)

        // связываем переменные в модели и ui
        bindings.homeViewModel = homeViewModel

        // наблюдаем за изменение текущей выбранной секции
        homeViewModel.currSectionMainData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                text_test.text = it.currentSectionId.toString()
            }
        })

        // наблюдаем за создание новой секции
        homeViewModel.openSectionCreateDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                val dialog : CreateSectionDialog = CreateSectionDialog()
            }
        })


        return bindings.root
    }
}
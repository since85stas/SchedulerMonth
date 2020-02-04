package stas.batura.schedulermonth.ui.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import androidx.databinding.DataBindingUtil



class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindings = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        val application : Application = requireNotNull(this.activity).application

        val database = LessonsDatabase.getInstance(application).lessonsDatabaseDao

        val viewModelFactory = HomeViewModelFactory( database, application)


//        homeViewModel =
//            ViewModelProviders.of(this).get(HomeViewModel::class.java)

         val homeViewModel = ViewModelProviders.of(this, viewModelFactory).get( HomeViewModel :: class.java)

//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return bindings.root
    }
}
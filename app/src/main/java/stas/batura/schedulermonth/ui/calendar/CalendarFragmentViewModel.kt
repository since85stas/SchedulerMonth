package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.*

class CalendarFragmentViewModel (val dataSource : LessonsDatabaseDao,
                                 val contex: Application,
                                 val sectionId : Long
                                 ): ViewModel() {

    val repository = Repository(dataSource)

//    val sectionId : Long = 1

    val lessonsList : LiveData<List<Lesson>> = repository.getLessonsByPeriodOnMainDataValFromDb(sectionId)

    val mainData : LiveData<MainData> = repository.getCurrentMainData()

    val periodList : LiveData<List<Period>> = repository.getPeriodsInSection(sectionId)


//    var sectionLive :LiveData<Section> =

    init {
        print("Calendar init")
//        mainData.observe(this, Observer {  })
    }





}
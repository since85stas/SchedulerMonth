package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.Lesson
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.MainData
import stas.batura.schedulermonth.repository.room.Section

class CalendarFragmentViewModel (val dataSource : LessonsDatabaseDao, val contex: Application): ViewModel() {

    val repository = Repository(dataSource)

    val sectionId : Long = 0

    val lessonsList : LiveData<List<Lesson>> = repository.getLessonsByPeriodOnMainDataValFromDb(sectionId)

    val mainData : LiveData<MainData> = repository.getCurrentMainData()

//    var sectionLive :LiveData<Section> =

    init {
        print("Calendar init")
//        mainData.observe(this, Observer {  })
    }



}
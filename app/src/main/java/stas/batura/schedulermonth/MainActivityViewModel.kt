package stas.batura.schedulermonth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section

class MainActivityViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel () {

    private var repository = Repository(dataSource)

    var sections : LiveData<List<Section>> = repository.getSections()

    fun setCurrentSection(sectionId: Long) {
        repository.setCurrentSection(sectionId)
    }


}
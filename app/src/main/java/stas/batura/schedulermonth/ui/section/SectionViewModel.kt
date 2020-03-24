package stas.batura.schedulermonth.ui.section

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.Lesson
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section

class SectionViewModel (val dataSource : LessonsDatabaseDao,
                        val contex: Application,
                        val sectionId : Long) : ViewModel() {

    // репозиторий
    private var repository: Repository = Repository(dataSource)

//    private var _titleLive : MutableLiveData<Section?> = MutableLiveData(null)
    public var sectionLive : LiveData<Section?>

    var lessonslive : LiveData<List<Lesson>>

    init {
        print("section view model created")
        sectionLive = repository.getSection(sectionId)

        lessonslive = repository.getLessonsInPeriod(sectionLive.value!!.sectionId, sectionLive.value!!.currentMonthId)
    }

    fun getSectionInfo (section : Section) {

        print("end")
    }


}
package stas.batura.schedulermonth.ui.section

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.Lesson
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Period
import stas.batura.schedulermonth.repository.room.Section

class SectionViewModel (val dataSource : LessonsDatabaseDao,
                        val contex: Application,
                        val sectionId : Long) : ViewModel() {

    // репозиторий
    private var repository: Repository = Repository(dataSource)

//    private var _titleLive : MutableLiveData<Section?> = MutableLiveData(null)
    public var sectionLive : LiveData<Section?>

    var lessonslive : MediatorLiveData<List<Lesson>> = MediatorLiveData()

    var currentLessonsListner: LiveData<List<Lesson>>? = null

    init {
        print("section view model created")
        sectionLive = repository.getSection(sectionId)
    }

    /**
     * получапет информацию о текущей секции
     */
    fun getSectionInfo (section : Section) {
        currentLessonsListner  = repository.getLessonsInPeriod(section.sectionId, section.currentMonthId)
        lessonslive.addSource(currentLessonsListner!!, Observer {
            lessonslive.value = it
        })
        print("end")
    }

    /**
     * вызывается при нажатии на кнопку некст
     */
    fun onNextButtonPressed() {
        repository.setCompletLessonInDb(sectionId)
    }

    /**
     * создает новый период и добавляет в базу
     */
    fun createNewPeriod() {
        val sectionId = sectionLive.value!!.sectionId
        val lessons   = sectionLive.value!!.lessonsInPeriod
        val newPeriod = sectionLive.value!!.currentMonthId + 1

        lessonslive.removeSource(currentLessonsListner!!)

        repository.updateCurrSectionValue(sectionId, newPeriod)

        insertNewPeriodLessons(sectionId, lessons, newPeriod)

        savePeriod()
    }

    /**
     * сохраняем информацию о новом периоде
     */
    private fun savePeriod() {
        val oldPeriod = repository.getPeriod(sectionLive.value!!.sectionId,
            sectionLive.value!!.currentMonthId)

        val period : Period = Period(sectionId,
            oldPeriod.periodId+1,
            oldPeriod.periodEndDate,
            oldPeriod.periodEndDate + sectionLive.value!!.timePeriodMillis)
        val periodId = repository.insertNewPeriod(period)
    }

    /**
     * создаем список уроков для нового месяца
     */
    private fun insertNewPeriodLessons(sectionId : Long, lessons: Int, newPeriod : Long) {
        for (i in 0 until lessons) {
            val lesson = Lesson  (
                0,
                newPeriod,
                sectionId
            )

            repository.insertLesson(lesson)
        }
    }


}
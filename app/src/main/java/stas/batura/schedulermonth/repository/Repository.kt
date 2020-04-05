package stas.batura.schedulermonth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.*

class Repository(private val dataSource: LessonsDatabaseDao) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val ioScopew = CoroutineScope(Dispatchers.IO + viewModelJob)

    //-----------------------------MAIN PART----------------------------------------------------
    /**
     * получаем информацию о выбранной по умолчанию секции
     */
    fun getCurrentMainData(): LiveData<MainData> {
        var res = dataSource.getCurrentMainData(44L)
        return res
    }
    /**
     * Выбранный секцию делаем записанным по умолчанию
     */
    fun setCurrentSectionInMain(sectionId: Long) {
        ioScopew.launch {
            saveMainDataValues(sectionId)
        }
    }

    /**
     * Сохраняем данные о секции в базе данных
     */
    private suspend fun saveMainDataValues(sectionId: Long) {
        withContext(Dispatchers.IO) {
            //            val mainData = MainData(44L, sectionId)
            dataSource.insertMainData(sectionId)
        }
    }

    fun saveMainDataValues(sectionId: Long, periodId: Long) {
        ioScopew.launch {
            dataSource.insertMainData(sectionId, periodId)
        }
    }

    fun saveMainDataPeriod(periodId: Long) {
        ioScopew.launch {
            dataSource.updateMainPeriodId(periodId)
        }
    }

    //-----------------------------SECTION PART-------------------------------------------------
    /**
     * сохраняем новую секцию и получаем ее номер
     */
    fun saveSection(section: Section) : Long {
        var result = 0L
        runBlocking {
            val job = async {
                val id = saveSectionDb(section)
                result = id
            }
            job.await()
        }
        return result
    }

    /**
     * добавляет данные о добавленной секции в базу данных
     */
    private suspend fun saveSectionDb(section: Section) : Long {
        return withContext(Dispatchers.IO) {
            val result = dataSource.insertSection(section)
            result
        }
    }

    /**
     * получаем информацию о секции из базы данных по номеру секции
     */
    fun getSection(key: Long): LiveData<Section?> {
        var section = dataSource.qetSection(key)
        return section
    }

    /**
     *  получает список всех секций из базы данных
     */
    fun getSections(): LiveData<List<Section>> {
        var sections = dataSource.getSections()
        return sections
    }

    //-----------------------------------PERIOD PART-----------------------------------------------
    fun updatePeriodIdInSectionInDb(sectionId: Long, periodId: Long) {
        ioScopew.launch {
            dataSource.updatePeriodIdInSection(sectionId, periodId)
        }
    }

    /**
     *
     */
    fun insertNewPeriod (period: Period) : Long {
        var result = 0L
        runBlocking {
            val job = async {
                val id = dataSource.insertPeriod(period)
                result = id
            }.await()
        }
        return result
    }

    /**
     * сохраняем новую секцию и получаем ее номер
     */
    fun getPeriod(sectionId: Long, periodId: Long) : Period {
        var result : Period? = null

        runBlocking {
            val job = async {
                val per = dataSource.getPeriodById(periodId, sectionId)
                result = per
            }
            job.await()
        }
        return result!!
    }


    fun updateCurrentPeriodNumInSectionInDb(sectionId: Long, newId : Long ) {
        ioScopew.launch {
            dataSource.updateCurrentPeriodNumInSection(sectionId, newId)
        }
    }

    fun getPeriodsInSection(sectionId: Long) : LiveData<List<Period>> {
        return dataSource.getPeriodsInSection(sectionId)
    }
    //------------------------------------LESSONS PART----------------------------------------------
    fun insertLesson (lesson: Lesson) {
        ioScopew.launch{
            dataSource.insertLesson(lesson)
        }
    }

    /**
     * получаем список урококв из БД
     */
    fun getLessonsInPeriod(sectionId: Long, periodId : Long) : LiveData<List<Lesson>>{
            val result = dataSource.getAllLessonsInPeriod(periodId, sectionId)
        return result
    }

    private fun addNewPeriodInDb() {

    }

    fun setCompletLessonInDb(sectionId: Long) {
        val lessonId = getNotComplLessonIdFromDb(sectionId)
            if (lessonId != null) {
            ioScopew.launch {
                dataSource.setNextLessonComplete(sectionId,lessonId)
            }
        } else {

        }
    }

    fun getNotComplLessonIdFromDb(sectionId: Long) : Long?{
        var result : Long?= 0L
        runBlocking {
            val job = async {
                val id = dataSource.getFirstNotCompleteLessonId(sectionId)
                result = id
            }
            job.await()
        }
        return result
    }

    fun getLessonsByPeriodOnMainDataValFromDb( sectionId: Long) : LiveData<List<Lesson>> {
        return dataSource.getLessonsByPeriodOnMainDataVal(sectionId)
    }
}



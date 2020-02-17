package stas.batura.schedulermonth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.MainData
import stas.batura.schedulermonth.repository.room.Section

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


//    fun getCurrentSection() : LiveData<MainData>{
//        uiScope.launch {
//            val res = getCurrentSection()
//        }
//    }

    /**
     *добавля т данные о добавленной секции в базу данных
     */
    suspend fun saveSectionInDb(section: Section) {
        withContext(Dispatchers.IO) {
            dataSource.insertSection(section)
        }
    }


//    private fun updateMainData() {
//        uiScope.launch {
//            getCurrentSectionFromDb()
//        }
//    }

    /**
     * получаем информацию о секции из базы данных по номеру секции
     */
    fun getSectionFromDb(key: Long): Section? {
//        return withContext(Dispatchers.IO) {
        var section = dataSource.qetSection(key)
        return section
//            section
//        }
    }


    /**
     * получаем информацию о выбранной по умолчанию секции
     */
    fun getCurrentSection(): LiveData<MainData> {
        var res = dataSource.getCurrentSection(44L)
        return res
    }

    /**
     *  получает список всех секций из базы данных
     */
    private fun getSectionsFromDb(): LiveData<List<Section>> {
        var sections = dataSource.getSections()
        return sections
    }

    private fun addNewPeriodInDb() {

    }

    /**
     * Выбранный секцию делаем записанным по умолчанию
     */
    private suspend fun setCurrentSection(sectionId: Int) {
        return withContext(Dispatchers.IO) {
            val mainData = MainData(44L, sectionId)
            dataSource.insertMainData(sectionId)
        }
    }
}



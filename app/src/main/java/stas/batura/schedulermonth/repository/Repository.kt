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


    fun saveSection(section: Section) {
        uiScope.launch {
            saveSectionDb(section)
        }
    }

    /**
     * добавля т данные о добавленной секции в базу данных
     */
    private suspend fun saveSectionDb(section: Section) {
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
    fun getSection(key: Long): LiveData<Section?> {
        var section = dataSource.qetSection(key)
        return section
    }

    fun getLastSection(): LiveData<Section?> {
        var section = dataSource.getLastSection()
        return section
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
    fun getSections(): LiveData<List<Section>> {
        var sections = dataSource.getSections()
        return sections
    }

    /**
     * Выбранный секцию делаем записанным по умолчанию
     */
    fun setCurrentSection(sectionId: Int) {
        uiScope.launch {
            setCurrentSectionDb(sectionId)
        }
    }

    /**
     * Сохраняем данные о секции в базе данных
     */
    private suspend fun setCurrentSectionDb(sectionId: Int) {
        return withContext(Dispatchers.IO) {
            val mainData = MainData(44L, sectionId)
            dataSource.insertMainData(sectionId)
        }
    }


    private fun addNewPeriodInDb() {

    }
}



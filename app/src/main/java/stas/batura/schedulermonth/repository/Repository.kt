package stas.batura.schedulermonth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.MainData

class Repository (private val dataSource: LessonsDatabaseDao)  {

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
     * получаем информацию о выбранной по умолчанию секции
     */
    private fun getCurrentSectionFromDb() : LiveData<MainData> {
//        return withContext(Dispatchers.IO) {
            var res = dataSource.getCurrentSection(44L)
            return res
//        }
    }
}
package stas.batura.schedulermonth.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section
import kotlin.text.Typography.section

class HomeViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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

    /**
     * инициализируем вьюмодель
     */
    init {
        uiScope.launch {
            val testSection = getSectionFromDb(1)
            print("test")
        }
    }

    /**
     *вызываетс для добавления новой секции
     */
    fun addSection() {
        uiScope.launch {
            val section = Section()
            saveSectionInDb(section)
        }
    }

    /**
     *добавля т данные о добавленной секции в базу данных
     */
    private suspend fun saveSectionInDb(section: Section) {
        withContext(Dispatchers.IO) {
            dataSource.insertSection(section)
        }
    }

    /**
     * получаем информацию о секции из базы данных по номеру секции
     */
    private suspend fun getSectionFromDb(key:Long) : Section? {
        return withContext(Dispatchers.IO) {
            var section = dataSource.qetSection(key)
            section
        }
    }


}
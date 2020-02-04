package stas.batura.schedulermonth.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section

class HomeViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    /**
     * инициализируем вьюмодель
     */
    init {

    }

    fun addSection(section: Section) {
        dataSource.insertSection(section)
    }
}
package stas.batura.schedulermonth.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.MainData

class HomeViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel() {

    private var repository: Repository = Repository(dataSource)

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private var _openSectionCreateDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val openSectionCreateDialog : LiveData<Boolean>
        get() = _openSectionCreateDialog

//    val currSectionMainData: LiveData<MainData>
//        get() = currSectionMainData
//    val currSectionMainData: LiveData<MainData>


    /**
     * создаем LiveData для текущей MainData
     */
    val currSectionMainData: LiveData<MainData> = repository.getCurrentMainData()

    /**
     * инициализируем вьюмодель
     */
    init {
        print("init")
    }

    /**
     *вызываетс для нажатии кнопки новой секции
     */
    fun addSectionButtonclicked() {
        _openSectionCreateDialog.value = true
//            val section = Section()
//            repository.saveSection(section)
    }

    /**
     * вызывается после добавления новой секции
     */
    fun addSectionComplete() {
        _openSectionCreateDialog.value = false
    }


}
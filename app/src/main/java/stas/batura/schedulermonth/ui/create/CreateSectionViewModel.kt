package stas.batura.schedulermonth.ui.create

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section
import stas.batura.schedulermonth.ui.utils.EditTextWatcher

class CreateSectionViewModel (val dataSource : LessonsDatabaseDao, val contex: Application): ViewModel() {

    // репозиторий
    private var repository: Repository = Repository(dataSource)

    private var _openHomeFragment: MutableLiveData<Boolean> = MutableLiveData(false)
    val openHomeFragment : LiveData<Boolean>
        get() = _openHomeFragment

    // слушатели для текста
    val nameTextWatcher : EditTextWatcher = EditTextWatcher()
    val lessonsTextWatcher : EditTextWatcher = EditTextWatcher()

    init {
        print("CreateSectionViewModel init")
    }

    /**
     * получение информации о новой секции из UI
     */
    private fun creteSection() : Section {
        val section = Section(
            nameTextWatcher.string,
            0,
            30,
            lessonsTextWatcher.string.toInt(),
            0
        )
        return section
    }

    /**
     * сохраняем секцию в БД
     */
    private fun saveSectionInDb(section: Section) {
        repository.saveSection(section)
    }

    /**
     * запускаем при нажатии на кнопку ОК
     */
    fun okButtonClicked() {
        val section = creteSection()
        saveSectionInDb(section)
        navigateToHome()
    }


    /**
     *вызываетс для нажатии кнопки новой секции
     */
    private fun navigateToHome() {
        _openHomeFragment.value = true
//            val section = Section()
//            repository.saveSection(section)
    }

    /**
     * вызывается после добавления новой секции
     */
    fun navigateToHomeFinish() {
        _openHomeFragment.value = false
    }


}
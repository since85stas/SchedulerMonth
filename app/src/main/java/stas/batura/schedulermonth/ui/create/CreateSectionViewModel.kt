package stas.batura.schedulermonth.ui.create

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.Lesson
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Period
import stas.batura.schedulermonth.repository.room.Section
import stas.batura.schedulermonth.ui.utils.IntegerTextWatcher
import stas.batura.schedulermonth.ui.utils.StringTextWatcher

class CreateSectionViewModel (val dataSource : LessonsDatabaseDao, val contex: Application): ViewModel() {

    // репозиторий
    private var repository: Repository = Repository(dataSource)

    private var _openHomeFragment: MutableLiveData<Boolean> = MutableLiveData(false)
    val openHomeFragment : LiveData<Boolean>
        get() = _openHomeFragment

    // лайв дэйта для навигации в фрагмент секции
    private var _openSectionFragment: MutableLiveData<Long?> = MutableLiveData(null)
    val openSectionFragment : LiveData<Long?>
        get() = _openSectionFragment

    // слушатели для текста
    val nameTextWatcher : StringTextWatcher = StringTextWatcher()
    val lessonsTextWatcher : IntegerTextWatcher = IntegerTextWatcher()

    var periodInMillis : Long = 0

    private var currentSection : Section? = null

    init {
        print("CreateSectionViewModel init")
    }

    /**
     * получение информации о новой секции из UI
     */
    private fun creteSection() : Section? {
        val lessons = lessonsTextWatcher.number
        if (lessons > 0) {

//            val period : Period = Period()
//            val periodId = repository.insertNewPeriod()

            // for month TODO:
            periodInMillis = 30*60*60*24;

            currentSection = Section(
                nameTextWatcher.string,
                0,
                periodInMillis,
                lessons,
                1
            )
            return currentSection
        } else {
            val toast = Toast.makeText(contex, "Wrong section format", Toast.LENGTH_LONG)
            toast.show()
            return null
        }
    }

    /**
     * создаем первоночальный список уроков
     */
    private fun createInitLessons(sectionId : Long, lessons: Int) {
        for (i in 0 until lessons) {
            val lesson = Lesson  (
                0,
                1,
                sectionId
            )

            repository.insertLesson(lesson)
        }
    }

    /**
     * запускаем при нажатии на кнопку ОК
     */
    fun okButtonClicked() {
        val section = creteSection()
        if (section != null) {
            val sectionId =  repository.saveSection(section)

            savePeriod(sectionId)

            createInitLessons(sectionId, section.lessonsInPeriod)
            navigateToSection(sectionId)
        }
    }

    private fun savePeriod(sectionId: Long) {
        //TODO: hardcodel 1
        val timeStart = System.currentTimeMillis()
        val timeEnd = timeStart + currentSection!!.timePeriodMillis

        val period : Period = Period(sectionId, 1, timeStart, timeEnd)
        val periodId = repository.insertNewPeriod(period)
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

    /**
     * вызывается при навигации в фрагмент секции
     */
    private fun navigateToSection( id:Long ) {
        _openSectionFragment.value = id
    }

    /**
     * вызывается после навигации в фрагмент секции
     */
    fun navigateToSectionFinish() {
        _openSectionFragment.value = null
    }
}
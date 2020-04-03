package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.ui.create.CreateSectionViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class CalendarFragmentViewModelFactory(
    private val dataSource: LessonsDatabaseDao,
    private val application: Application,
    private val sectionId : Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarFragmentViewModel::class.java)) {
            return CalendarFragmentViewModel(dataSource, application, sectionId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
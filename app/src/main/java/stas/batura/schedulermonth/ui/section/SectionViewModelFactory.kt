package stas.batura.schedulermonth.ui.section

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
class SectionViewModelFactory(
    private val dataSource: LessonsDatabaseDao,
    private val application: Application,
    private val id : Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SectionViewModel::class.java)) {
            return SectionViewModel(dataSource, application, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
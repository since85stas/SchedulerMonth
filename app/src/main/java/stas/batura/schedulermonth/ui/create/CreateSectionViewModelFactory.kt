package stas.batura.schedulermonth.ui.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.ui.home.HomeViewModel

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class CreateSectionViewModelFactory(
    private val dataSource: LessonsDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateSectionViewModel::class.java)) {
            return CreateSectionViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package stas.batura.schedulermonth.ui.calendar

import android.app.Application
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao

class CalendarFragmentViewModel (val dataSource : LessonsDatabaseDao, val contex: Application): ViewModel() {

}
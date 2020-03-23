package stas.batura.schedulermonth.ui.section

import android.app.Application
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao

class SectionViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel() {



}
package stas.batura.schedulermonth

import android.app.Application
import androidx.lifecycle.ViewModel
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao

class MainActivityViewModel (val dataSource : LessonsDatabaseDao, val contex: Application) : ViewModel () {

    private var repository = Repository(dataSource)


}
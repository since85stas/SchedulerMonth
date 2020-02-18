package stas.batura.schedulermonth.ui.create

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.repository.Repository
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao

class CreateSectionFragment () : DialogFragment () {

    lateinit var repository : Repository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        // создаем репозиторий
        val dao : LessonsDatabaseDao = LessonsDatabase.getInstance(activity!!).lessonsDatabaseDao
        repository = Repository(dao)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.create_section_dialog, null)
        builder.setView(view)
            .setPositiveButton("ok", null)
            .setNegativeButton("no",null)

        return builder.create()
    }


}
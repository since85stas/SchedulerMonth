package stas.batura.schedulermonth.ui.section

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lessons_inn_section_item.view.*
import stas.batura.schedulermonth.R
import stas.batura.schedulermonth.repository.room.Lesson

class LessonsAdapter (private val data : List<Lesson>)
    : RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    class LessonsViewHolder(val view: View) : RecyclerView.ViewHolder(view) , LayoutContainer {

        fun bind(item: Lesson) {
//            view.preview_list_image.
            view.title_text.text = "Lessno ${item.lessonId}"

            view.lesson_date.text = item.lessonDate.toString()
        }

        override val containerView: View?
            get() = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lessons_inn_section_item,parent,false)
        return LessonsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        holder.bind(data.get(position))
    }
}
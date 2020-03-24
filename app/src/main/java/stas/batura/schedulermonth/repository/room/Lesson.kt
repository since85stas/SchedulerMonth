package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "lessons_count_table"
    , foreignKeys = [ForeignKey(entity = Section::class
        , parentColumns = ["Id"]
        , childColumns = ["section_id"]
        , onDelete = CASCADE)])
data class Lesson(

    @ColumnInfo(name = "lesson_is_complete")
    var lessonIsComplete: Int = 0,

    @ColumnInfo(name = "month_id")
    var monthId: Long = -1,

    @ColumnInfo(name = "section_id")
    var sectionId : Long = -1

) {
    @PrimaryKey(autoGenerate = true)
    var lessonId: Long = 0L

    @ColumnInfo(name = "lesson_date")
    var lessonDate: Long = System.currentTimeMillis()
}
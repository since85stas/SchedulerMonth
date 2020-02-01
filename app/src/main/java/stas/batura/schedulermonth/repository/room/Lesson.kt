package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons_count_table")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var lessonId: Long = 0L,

    @ColumnInfo(name = "lesson_date")
    val lessonDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "lesson_is_complete")
    var endTimeMilli: Long = 0,

    @ColumnInfo(name = "month_id")
    var monthId: Int = -1,

    @ColumnInfo(name = "section_id")
    var sectionId : Int = -1
)
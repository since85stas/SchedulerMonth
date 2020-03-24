package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections_table")
data class Section(

    @ColumnInfo(name = "section_name")
    val sectionName: String = "Default name",

    @ColumnInfo(name = "section_type")
    var sectionType: Long = 0,

    @ColumnInfo(name = "time_period_days")
    var timePeriodDays: Int = 30,

    @ColumnInfo(name = "lessons_in_period")
    var lessonsInPeriod : Int = 0,

    @ColumnInfo(name = "current_month_id")
    var currentMonthId : Long = 0

) {
    @PrimaryKey(autoGenerate = true )
    @ColumnInfo(name = "Id")
    var sectionId: Long = 0L

    @ColumnInfo(name = "section_creation_time")
    var sectionCreatedTime:Long = System.currentTimeMillis()
}
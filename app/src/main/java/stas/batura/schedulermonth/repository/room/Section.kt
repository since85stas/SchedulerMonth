package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections_table")
data class Section(
    @PrimaryKey(autoGenerate = true)
    var sectionId: Long = 0L,

    @ColumnInfo(name = "section_name")
    val sectionName: String = "Default$sectionId",

    @ColumnInfo(name = "section_type")
    var sectionType: Long = 0,

    @ColumnInfo(name = "time_period_days")
    var timePeriodDays: Int = 30,

    @ColumnInfo(name = "lessons_in_period")
    var lessonsInPeriod : Int = 0,

    @ColumnInfo(name = "current_month_id")
    var currentMonthId : Int = 0
)
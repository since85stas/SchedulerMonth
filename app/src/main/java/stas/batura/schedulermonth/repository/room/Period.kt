package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "period_count_table"
    , foreignKeys = [ForeignKey(entity = Section::class
        , parentColumns = ["Id"]
        , childColumns = ["section_id"]
        , onDelete = ForeignKey.CASCADE
    )])
data class Period(

//
//    @ColumnInfo(name = "")
//    var lessonIsComplete: Int = 0,

    @ColumnInfo(name = "section_id")
    var sectionId : Int = -1

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "periodId")
    var periodId: Long = 0

    @ColumnInfo(name = "period_start_date")
    var periodStartDate: Long = System.currentTimeMillis()
}
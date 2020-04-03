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

    @ColumnInfo(name = "section_id")
    var sectionId : Long = -1,

    @ColumnInfo(name = "period_start_date")
    var periodStartDate: Long,

    @ColumnInfo(name = "perid_end_date")
    var periodEndDate:Long
) {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "period_id")
    var periodId: Long = 0
}

//data class Period(
//
//    @ColumnInfo(name = "section_id")
//    var sectionId : Long = -1,
//
//    @PrimaryKey
//    @ColumnInfo(name = "period_id")
//    var periodId: Long = 0) {
//
//    @ColumnInfo(name = "period_start_date")
//    var periodStartDate: Long = System.currentTimeMillis()
//}


package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "main_table", foreignKeys = [ForeignKey(entity = Section::class
    , parentColumns = ["Id"]
    , childColumns = ["current_section_id"]
    , onDelete = ForeignKey.CASCADE
)])
data class MainData(

    @PrimaryKey
    var mainId: Long = 0L,

    @ColumnInfo(name = "current_section_id")
    var currentSectionId: Long = -1 ) {

    @ColumnInfo(name = "current_period_id")
    var currentPeriodId : Long = -1
}

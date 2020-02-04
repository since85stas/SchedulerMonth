package stas.batura.schedulermonth.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_table")
data class MainData(
    @PrimaryKey(autoGenerate = true)
    var mainId: Long = 0L,

    @ColumnInfo(name = "current_section_id")
    var currentSectionId : Int = 0
)
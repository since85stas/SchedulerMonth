package stas.batura.schedulermonth.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

private val CURR_ID : Long = 44L

@Dao
abstract class LessonsDatabaseDao {

    @Insert abstract fun insertSection(section: Section)

    @Query("SELECT * FROM sections_table WHERE Id = :key")
    abstract fun qetSection(key : Long) : Section?

    @Query("SELECT * FROM sections_table ORDER BY Id")
    abstract fun getSections() : List<Section>



    /**
     * записываем номер выбранной секции
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertMainData(sectionId: Int) {
        val mainData = MainData(CURR_ID, sectionId)
    }

    @Query ("SELECT * FROM main_table WHERE mainId = :key")
    internal abstract fun getCurrentSection( key: Long) : MainData
}
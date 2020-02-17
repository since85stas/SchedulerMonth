package stas.batura.schedulermonth.repository.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

private val CURR_ID : Long = 44L

@Dao
abstract class LessonsDatabaseDao {

    @Insert
    abstract fun insertSection(section: Section)

    @Query("SELECT * FROM sections_table WHERE Id = :key")
    abstract fun qetSection(key: Long): LiveData<Section?>

    @Query("SELECT * FROM sections_table ORDER BY Id")
    abstract fun getSections(): LiveData<List<Section>>

    /**
     * записываем номер выбранной секции
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertMainDataDb(mainData: MainData)

    fun insertMainData(sectionId: Int) {
        val mainData = MainData(CURR_ID, sectionId)
        insertMainDataDb(mainData)
    }

    /**
     * получаем номер выбранной секции в LiveData
     */
    @Query("SELECT * FROM main_table WHERE mainId = :key")
    abstract fun getCurrentSection(key: Long): LiveData<MainData>
}
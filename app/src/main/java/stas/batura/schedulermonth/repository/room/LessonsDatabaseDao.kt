package stas.batura.schedulermonth.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface LessonsDatabaseDao {

    @Insert fun insertSection(section: Section)

    @Query("SELECT * FROM sections_table WHERE Id = :key")
    fun qetSection(key : Long) : Section?

    @Query("SELECT * FROM sections_table ORDER BY Id")
    fun getSections() : List<Section>



    /**
     * записываем номер выбранной секции
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertMainData(mainData: MainData)

}
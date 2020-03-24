package stas.batura.schedulermonth.repository.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.concurrent.atomic.AtomicLongFieldUpdater

private val CURR_ID : Long = 44L

@Dao
abstract class LessonsDatabaseDao {

    @Insert
    abstract fun insertSection(section: Section) : Long

    @Query("SELECT * FROM sections_table WHERE Id = :key")
    abstract fun qetSection(key: Long): LiveData<Section?>

//    @Query(value = "SELECT LAST(Id) FROM sections_table ")
//    abstract fun getLastSection() : LiveData<Section?>

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

    /**
     * создает новый временной период в БД
     */
    @Insert
    abstract fun insertPeriod(period: Period)

    /**
     * информация о конкретном периоде
     */
    @Query("SELECT * FROM period_count_table WHERE periodId = :id")
    abstract fun getPeriodById(id: Long) : Period

    /**
     * все периоды
     */
    @Query("SELECT * FROM period_count_table ORDER BY period_start_date")
    abstract fun getAllPeriods() : List<Period>

    /**
     * записываем информацию о уроках
     */
    @Insert
    abstract fun insertLesson(lesson: Lesson)

    /**
     *  получаем все уроки в секции за выбранный период
     */
    @Query(  "SELECT * FROM lessons_count_table WHERE section_id = :sectionId AND month_id = :periodId  ORDER BY lessonId")
    abstract fun getAllLessonsInPeriod (periodId : Long, sectionId: Long) : LiveData<List<Lesson>>
}
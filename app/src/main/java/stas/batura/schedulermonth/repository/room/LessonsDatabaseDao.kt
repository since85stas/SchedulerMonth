package stas.batura.schedulermonth.repository.room


import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.concurrent.atomic.AtomicLongFieldUpdater

private val CURR_ID : Long = 44L

@Dao
abstract class LessonsDatabaseDao {


    //---------------------------MAIN DATA---------------------------------------------------------
    /**
     * записываем номер выбранной секции
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertMainDataDb(mainData: MainData)

    fun insertMainData(sectionId: Long) {
        val mainData = MainData(CURR_ID, sectionId)
        insertMainDataDb(mainData)
    }

    fun insertMainData(sectionId: Long, periodId: Long) {
        val mainData = MainData(CURR_ID, sectionId)
        mainData.currentPeriodId = periodId
        insertMainDataDb(mainData)
    }

    @Query ("UPDATE main_table SET current_period_id = :periodId")
    abstract suspend fun updateMainPeriodId (periodId: Long)


    //--------------------------------SECTION PART-------------------------------------------------
    @Insert
    abstract fun insertSection(section: Section) : Long

    @Query("SELECT * FROM sections_table WHERE Id = :key")
    abstract fun qetSection(key: Long): LiveData<Section?>

//    @Query(value = "SELECT LAST(Id) FROM sections_table ")
//    abstract fun getLastSection() : LiveData<Section?>

    @Query("SELECT * FROM sections_table ORDER BY Id")
    abstract fun getSections(): LiveData<List<Section>>

    /**
     * получаем номер выбранной секции в LiveData
     */
    @Query("SELECT * FROM main_table WHERE mainId = :key")
    abstract fun getCurrentMainData(key: Long): LiveData<MainData>

    /**
     * обновляем номер периода в секции
     */
    @Query("UPDATE sections_table SET current_month_id =:periodId WHERE Id = :sectionId")
    abstract suspend fun updatePeriodIdInSection(sectionId: Long, periodId: Long)

    //----------------------------------PERIOD PART-------------------------------------------------
    /**
     * создает новый временной период в БД
     */
    @Insert
    abstract suspend fun insertPeriod(period: Period) : Long

    /**
     * информация о конкретном периоде
     */
    @Query("SELECT * FROM period_count_table WHERE period_id = :periodId AND section_id =:sectionId")
    abstract fun getPeriodById(periodId: Long, sectionId: Long) : Period

    /**
     * обновляем информацию о текущем месяце
     */
    @Query("UPDATE sections_table SET current_month_id = :newMonthId WHERE Id = :sectionId")
    abstract fun updateCurrentPeriodNumInSection(sectionId: Long, newMonthId : Long )

    /**
     * получаем все периоды в данной секции
     */
    @Query("SELECT * FROM period_count_table WHERE section_id =:sectionId")
    abstract fun getPeriodsInSection(sectionId: Long) : LiveData<List<Period>>

    //--------------------------------LESSONS PART-------------------------------------
    /**
     *
     */
    @Query("SELECT * FROM lessons_count_table WHERE section_id = :sectionId AND month_id IN ( SELECT current_period_id FROM main_table )"  )
    abstract fun getLessonsByPeriodOnMainDataVal( sectionId: Long) : LiveData<List<Lesson>>

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

    /**
     * записываем что урок закончен по его номеру
     */
    @Query("UPDATE lessons_count_table SET lesson_is_complete = 1 WHERE section_id = :sectionId AND lessonId = :lessonId")
    abstract fun setNextLessonComplete(sectionId: Long, lessonId : Long)

    /**
     * записываем что урок НЕ закончен по его номеру
     */
    @Query("UPDATE lessons_count_table SET lesson_is_complete = 0 WHERE section_id = :sectionId AND lessonId = :lessonId")
    abstract fun setNextLessonNoComplete(sectionId: Long, lessonId : Long?)

    /**
     * получает номер последнего неоконченного урока в последнем периоде
     */
    @Query("SELECT lessonId FROM lessons_count_table WHERE section_id = :sectionId AND lesson_is_complete = 0 ORDER BY lessonId")
    abstract suspend fun getFirstNotCompleteLessonId(sectionId: Long) : Long?


}
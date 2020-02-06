package stas.batura.schedulermonth

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.LessonsDatabase
import stas.batura.schedulermonth.repository.room.LessonsDatabaseDao
import stas.batura.schedulermonth.repository.room.Section

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var dataSource : LessonsDatabaseDao

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val viewScope = CoroutineScope(Dispatchers.Main + viewJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        dataSource = LessonsDatabase.getInstance(application).lessonsDatabaseDao

        // получаем список секций для отображения в navView
        viewScope.launch {
            val sec = getSectionsFromDb()
            print("act test")

            // создаем меню по загруженным данным
            createSectionsInMenu(sec)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * создает отображение списка секций в меню
     */
    private fun createSectionsInMenu(sections : List<Section>) {
        var menu = nav_view.menu
        for (section in sections) {
            menu.add(R.id.mainGroup,section.sectionId.toInt(),2, section.sectionName)
        }

    }

    /**
     *  получает список всех секций из базы данных
     */
    private suspend fun getSectionsFromDb() : List<Section> {
        return withContext(Dispatchers.IO) {
            var sections = dataSource.getSections()
            sections
        }
    }
}

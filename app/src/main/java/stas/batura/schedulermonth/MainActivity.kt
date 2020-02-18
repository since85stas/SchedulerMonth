package stas.batura.schedulermonth

import android.os.Bundle
import android.util.Log
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
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import stas.batura.schedulermonth.repository.room.*
import stas.batura.schedulermonth.ui.home.HomeViewModelFactory


class MainActivity : AppCompatActivity() {

    val SECT_GROUP_ID : Int = 4477

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

    private lateinit var mainActivityViewModel: MainActivityViewModel

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

        createBasicNavView()

        dataSource = LessonsDatabase.getInstance(application).lessonsDatabaseDao

        val factory = MainActivityViewModelFactory(dataSource,application)
        mainActivityViewModel = ViewModelProviders.of(this,factory)
            .get(MainActivityViewModel::class.java)


        // получаем список секций для отображения в navView
        mainActivityViewModel.sections.observe(this, Observer {
            if (it != null ) {
                createSectionsInMenu(it)
            }
        })
    }

    private fun createBasicNavView() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        var listId: MutableList<Int> = ArrayList()

        var count : Int = 0

        // если уже присутствуют секции то их удаляем
        menu.removeGroup(SECT_GROUP_ID)

        for (section in sections) {
            menu.add(SECT_GROUP_ID,section.sectionId.toInt(),2, section.sectionName + count  )
            count++
            listId.add(section.sectionId.toInt())
        }

        // устанавливаем слушатель на нажатие клавиш
        nav_view.setNavigationItemSelectedListener( (NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Log.d("main", "Home")
                    drawer_layout.closeDrawers()
                    true
                }
                in listId ->  {
                    Log.d("main", "frag$listId")
                        val result = mainActivityViewModel.setCurrentSection(it.itemId)
                    drawer_layout.closeDrawers()
                   true
                }
                else -> false
            }
        }) )

    }



}

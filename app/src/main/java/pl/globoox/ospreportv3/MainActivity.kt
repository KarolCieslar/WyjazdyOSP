package pl.globoox.ospreportv3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import pl.globoox.ospreportv3.databinding.ActivityMainBinding
import pl.globoox.ospreportv3.ui.action.list.ListActionFragment
import pl.globoox.ospreportv3.ui.salary.SalaryFragment
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT)
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
    }

    private lateinit var binding: ActivityMainBinding
    private var currentFragmentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.red)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listAction, R.id.salaryFragment, R.id.forcesFragment, R.id.addOrEditAction
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentFragmentId = destination.id
            when (destination.id) {
                R.id.addOrEditAction -> navView.isVisible = false
                else -> navView.isVisible = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.helpIcon -> {
                val string = when (currentFragmentId) {
                    R.id.listAction -> R.string.help_view_action_list
                    R.id.addOrEditAction -> R.string.help_view_add_or_edit_action_step_one
                    R.id.salaryFragment -> R.string.help_view_salary
                    R.id.forcesFragment -> R.string.help_view_forces
                    else -> R.string.button_add_action
                }
                Toast.makeText(this, this.resources.getString(string), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun showSnackBar(text: String) {
        Snackbar.make(
            binding.snackbarContainer,
            text,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
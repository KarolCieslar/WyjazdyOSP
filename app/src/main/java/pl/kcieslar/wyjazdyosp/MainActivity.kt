package pl.kcieslar.wyjazdyosp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kcieslar.wyjazdyosp.R
import kcieslar.wyjazdyosp.databinding.ActivityMainBinding
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes
import pl.kcieslar.wyjazdyosp.views.HelpDialogView
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
    private var helpDialogStringRes = HelpDialogStringRes.ACTION_LIST
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        throw RuntimeException("Test Crash") // Force a crash

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
                showHelpDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setHelpDialogString(helpDialogStringRes: HelpDialogStringRes) {
        this.helpDialogStringRes = helpDialogStringRes
    }

    private fun showHelpDialog() {
        val stringRes = when (helpDialogStringRes) {
            HelpDialogStringRes.ACTION_LIST -> R.string.help_view_action_list
            HelpDialogStringRes.SALARY -> R.string.help_view_salary
            HelpDialogStringRes.FORCES -> R.string.help_view_forces
            HelpDialogStringRes.ADD_ACTION_STEP_ONE -> R.string.help_view_add_or_edit_action_step_one
            HelpDialogStringRes.ADD_ACTION_STEP_SECOND -> R.string.help_view_add_or_edit_action_step_second
            HelpDialogStringRes.ADD_ACTION_STEP_THIRD -> R.string.help_view_add_or_edit_action_step_third
        }
        val dialog = HelpDialogView(this)
        dialog.setDescription(this.resources.getString(stringRes))
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
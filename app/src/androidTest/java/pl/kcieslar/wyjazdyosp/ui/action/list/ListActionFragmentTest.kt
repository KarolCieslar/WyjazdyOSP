package pl.kcieslar.wyjazdyosp.ui.action.list

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import pl.kcieslar.wyjazdyosp.MainActivity
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.launchFragmentInHiltContainer

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ListActionFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddActionButton_navigateToAddActionFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ListActionFragment> {
             Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.floatingActionButton)).perform(click())
        verify(navController).navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(null))
    }
}
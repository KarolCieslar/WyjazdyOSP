package pl.kcieslar.wyjazdyosp.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class ActionDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var actionDao: ActionDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()

        actionDao = database.actionDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun addAction() = runBlocking {
        val action = Action(
            id = 1,
            outTime = "test",
            inTime = "15:20",
            location = "Lubawka",
            number = "15",
            description = "Description of Action",
            carsInAction = listOf(),
            equipment = listOf()
        )
        actionDao.addAction(action)

        val allActions = actionDao.getAllActions().getOrAwaitValue()
        assertThat(allActions).contains(action)
    }

    @Test
    fun editAction() = runBlocking {
        val action = Action(
            id = 1,
            outTime = "test",
            inTime = "15:20",
            location = "Lubawka",
            number = "15",
            description = "Description of Action",
            carsInAction = listOf(),
            equipment = listOf()
        )
        actionDao.addAction(action)

        val editedAction = Action(
            id = 1,
            outTime = "test",
            inTime = "15:20",
            location = "Wałbrzych",
            number = "1541",
            description = "Description of Action",
            carsInAction = listOf(),
            equipment = listOf()
        )
        actionDao.editAction(editedAction)

        val allActions = actionDao.getAllActions().getOrAwaitValue()
        assertThat(allActions).contains(editedAction)
    }

    @Test
    fun removeAction() = runBlocking {
        val action = Action(
            id = 1,
            outTime = "test",
            inTime = "15:20",
            location = "Lubawka",
            number = "15",
            description = "Description of Action",
            carsInAction = listOf(),
            equipment = listOf()
        )
        actionDao.addAction(action)
        actionDao.removeAction(action)

        val allActions = actionDao.getAllActions().getOrAwaitValue()
        assertThat(allActions).doesNotContain(action)
    }

}
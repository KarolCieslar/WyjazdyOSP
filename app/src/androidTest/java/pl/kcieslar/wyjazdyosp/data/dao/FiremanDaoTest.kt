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
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class FiremanDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var firemanDao: FiremanDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()

        firemanDao = database.firemanDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun removeFireman() = runBlocking {
        val fireman = Fireman(
            id = 1,
            name = "Fireman1"
        )
        firemanDao.addFireman(fireman)
        firemanDao.removeFireman(fireman)

        val allFiremans = firemanDao.getAllFiremans().getOrAwaitValue()
        assertThat(allFiremans).doesNotContain(fireman)
    }

    @Test
    fun addFireman() = runBlocking {
        val fireman = Fireman(
            id = 1,
            name = "Fireman1"
        )
        firemanDao.addFireman(fireman)

        val allFiremans = firemanDao.getAllFiremans().getOrAwaitValue()
        assertThat(allFiremans).contains(fireman)
    }

    @Test
    fun editFireman() = runBlocking {
        val fireman = Fireman(
            id = 1,
            name = "Fireman1"
        )
        firemanDao.addFireman(fireman)

        val editFireman = Fireman(
            id = 1,
            name = "FiremanEdited"
        )
        firemanDao.editFireman(editFireman)

        val allFiremans = firemanDao.getAllFiremans().getOrAwaitValue()
        assertThat(allFiremans).contains(editFireman)
    }
}
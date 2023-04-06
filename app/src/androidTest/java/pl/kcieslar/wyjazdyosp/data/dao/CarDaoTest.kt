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
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class CarDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var carDao: CarDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()

        carDao = database.carDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun removeCar() = runBlocking {
        val car = Car(
            id = 1,
            name = "Car1"
        )
        carDao.addCar(car)
        carDao.removeCar(car)

        val allCars = carDao.getAllCars().getOrAwaitValue()
        assertThat(allCars).doesNotContain(car)
    }

    @Test
    fun addCar() = runBlocking {
        val car = Car(
            id = 1,
            name = "Car1"
        )
        carDao.addCar(car)

        val allCars = carDao.getAllCars().getOrAwaitValue()
        assertThat(allCars).contains(car)
    }

    @Test
    fun editCar() = runBlocking {
        val car = Car(
            id = 1,
            name = "Car1"
        )
        carDao.addCar(car)

        val editCar = Car(
            id = 1,
            name = "CarEdited"
        )
        carDao.editCar(editCar)

        val allCars = carDao.getAllCars().getOrAwaitValue()
        assertThat(allCars).contains(editCar)
    }
}
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
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.utils.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class EquipmentDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MainDatabase
    private lateinit var equipmentDao: EquipmentDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java
        ).allowMainThreadQueries().build()

        equipmentDao = database.equipmentDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun removeEquipment() = runBlocking {
        val equipment = Equipment(
            id = 1,
            name = "Equipment1"
        )
        equipmentDao.addEquipment(equipment)
        equipmentDao.removeEquipment(equipment)

        val allEquipments = equipmentDao.getAllEquipments().getOrAwaitValue()
        assertThat(allEquipments).doesNotContain(equipment)
    }

    @Test
    fun addEquipment() = runBlocking {
        val equipment = Equipment(
            id = 1,
            name = "Equipment1"
        )
        equipmentDao.addEquipment(equipment)

        val allEquipments = equipmentDao.getAllEquipments().getOrAwaitValue()
        assertThat(allEquipments).contains(equipment)
    }

    @Test
    fun editEquipment() = runBlocking {
        val equipment = Equipment(
            id = 1,
            name = "Equipment1"
        )
        equipmentDao.addEquipment(equipment)

        val editEquipment = Equipment(
            id = 1,
            name = "EquipmentEdited"
        )
        equipmentDao.editEquipment(editEquipment)

        val allEquipments = equipmentDao.getAllEquipments().getOrAwaitValue()
        assertThat(allEquipments).contains(editEquipment)
    }
}
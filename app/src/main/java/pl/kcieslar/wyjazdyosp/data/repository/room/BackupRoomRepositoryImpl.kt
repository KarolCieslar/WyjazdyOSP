package pl.kcieslar.wyjazdyosp.data.repository.room

import pl.kcieslar.wyjazdyosp.domain.repository.room.BackupRoomRepository
import pl.kcieslar.wyjazdyosp.model.room.MainDataBackupDao
import pl.kcieslar.wyjazdyosp.model.room.RoomCar
import javax.inject.Inject

class BackupRoomRepositoryImpl @Inject constructor(
    private val backupDao: MainDataBackupDao
) : BackupRoomRepository {

    override fun getCars(): List<RoomCar> {
        return backupDao.getAllCars()
    }
}
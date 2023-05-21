package pl.kcieslar.wyjazdyosp.domain.repository.room

import pl.kcieslar.wyjazdyosp.model.room.RoomCar

interface BackupRoomRepository {

    fun getCars(): List<RoomCar>
}
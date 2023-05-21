package pl.kcieslar.wyjazdyosp.ui.backup_room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kcieslar.wyjazdyosp.data.repository.room.BackupRoomRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class BackupRoomViewModel @Inject constructor(
    private val backupRoomRepository: BackupRoomRepositoryImpl
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    fun getBackup() {
        val cars = backupRoomRepository.getCars()
        cars.forEach {
            Log.d("adsdasdas", it.name)
        }
    }

    inner class SuccessfulGetForceByKey(val force: Forces) : ViewModelEvent()
    inner class CrudItemError(val exception: Exception?, val retryAction: (() -> Unit)) : ViewModelEvent()
}
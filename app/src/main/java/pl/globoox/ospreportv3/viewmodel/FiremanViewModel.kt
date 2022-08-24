package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.repository.FiremanRepository

class FiremanViewModel(application: Application) : AndroidViewModel(application) {

    val getAllFiremans: LiveData<List<Fireman>>
    private val repository: FiremanRepository

    init {
       val firemanDao = MainDatabase.getFiremansDatabase(application).firemanDao()
        repository = FiremanRepository(firemanDao)
        getAllFiremans = repository.getAllFiremans
    }

    fun addFireman(fireman: Fireman) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFireman(fireman)
        }
    }
}
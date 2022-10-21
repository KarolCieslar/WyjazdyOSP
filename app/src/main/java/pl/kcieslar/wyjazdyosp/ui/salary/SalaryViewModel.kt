package pl.kcieslar.wyjazdyosp.ui.salary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.repository.FiremanRepository

class SalaryViewModel(application: Application) : AndroidViewModel(application) {

    val firemanList: LiveData<List<Fireman>>
    val firemanActions: LiveData<List<Action>>
    private val firemanRepository: FiremanRepository
    var dateButtonSelected = false

    init {
        val database = MainDatabase.getFiremansDatabase(application)
        firemanRepository = FiremanRepository(database.firemanDao())
        firemanList = firemanRepository.getAllFiremans
        firemanActions = firemanRepository.getAllFiremanActions
    }
}
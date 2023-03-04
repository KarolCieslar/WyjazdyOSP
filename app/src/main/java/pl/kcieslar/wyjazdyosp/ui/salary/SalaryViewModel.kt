package pl.kcieslar.wyjazdyosp.ui.salary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.data.repository.FiremanRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val application: Application,
    private val firemanRepository: FiremanRepositoryImpl
) : AndroidViewModel(application) {

    val firemanList: LiveData<List<Fireman>> = firemanRepository.getAllFiremans
    val firemanActions: LiveData<List<Action>> = firemanRepository.getAllFiremanActions
    var dateButtonSelected = false

}
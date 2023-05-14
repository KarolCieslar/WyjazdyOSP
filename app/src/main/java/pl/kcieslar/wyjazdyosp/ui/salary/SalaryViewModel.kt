package pl.kcieslar.wyjazdyosp.ui.salary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl
) : ViewModel() {

//    val firemanList: LiveData<List<Fireman>> = firemanRepository.getAllFiremans
//    val firemanActions: LiveData<List<Action>> = firemanRepository.getAllFiremanActions
    var dateButtonSelected = false

}
package pl.kcieslar.wyjazdyosp.ui.status.join

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kcieslar.wyjazdyosp.base.BaseViewModel
import pl.kcieslar.wyjazdyosp.data.repository.impl.StatusRepositoryImpl
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class JoinGroupBottomDialogViewModel @Inject constructor(
    private val statusRepository: StatusRepositoryImpl
) : BaseViewModel() {

    private val _viewModelEvents = MutableLiveData<SingleLiveEvent<ViewModelEvent?>>()
    val viewModelEvents: LiveData<SingleLiveEvent<ViewModelEvent?>> get() = _viewModelEvents

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val groupCode = MutableLiveData(mutableListOf("", "", "", "", "", ""))

    val createButtonEnabled = MutableLiveData(false)

    fun onGroupNumberChange(index: Int, editable: Editable) {
        val current = groupCode.value
        current?.set(index, editable.toString())
        groupCode.value = current
        createButtonEnabled.value = groupCode.value?.joinToString("")?.length == 6
        _viewModelEvents.value = SingleLiveEvent(EditTextChanged(index + 1))
    }

    fun joinGroup() {
//        viewModelScope.launch {
//            if (statusRepository.joinGroup(groupCode.value?.joinToString("").orEmpty())) navigate(JoinGroupBottomDialogDirections.actionToGroupListFragment()) else
//                showMessage("Grupa o podanym kodzie nie istnieje")
//        }
    }

    inner class EditTextChanged(val index: Int) : ViewModelEvent()
}
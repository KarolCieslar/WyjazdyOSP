package pl.kcieslar.wyjazdyosp.ui.status.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.base.BaseViewModel
import pl.kcieslar.wyjazdyosp.data.repository.impl.StatusRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Group
import javax.inject.Inject

@HiltViewModel
class StatusGroupListViewModel @Inject constructor(
    private val statusRepository: StatusRepositoryImpl
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _groupList = MutableLiveData<List<Group>>()
    val groupList: LiveData<List<Group>>
        get() = _groupList

    init {
        observeGroupsList()
    }

    private fun observeGroupsList() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _groupList.value = statusRepository.getGroups()
            } catch (_: Exception) {

            }
        }
        _isLoading.value = false
    }

    fun navigateToAddGroupFragment() {
        navigate(StatusGroupListFragmentDirections.actionToAddOrCreateGroupBottomDialog())
    }
}
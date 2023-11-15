package pl.kcieslar.wyjazdyosp.ui.status.create

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kcieslar.wyjazdyosp.base.BaseViewModel
import pl.kcieslar.wyjazdyosp.data.repository.impl.AuthRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.StatusRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Group
import pl.kcieslar.wyjazdyosp.model.User
import pl.kcieslar.wyjazdyosp.model.UserStatus
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CreateGroupBottomDialogViewModel @Inject constructor(
    private val statusRepository: StatusRepositoryImpl,
    private val authRepository: AuthRepositoryImpl
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val groupNameEditText = MutableLiveData("")
    private val groupDescriptionEditText = MutableLiveData("")
    val createButtonEnabled = MutableLiveData(false)

    private fun createGroup(groupCode: String) {
//        launchCatching(invokeOnCompletion = {
//            navigate(CreateGroupBottomDialogDirections.actionToGroupListFragment())
//        }) {
//            statusRepository.createNewGroup(
//                group = Group(
//                    adminKey = authRepository.getUserUid(),
//                    code = groupCode,
//                    name = groupNameEditText.value.toString(),
//                    description = groupDescriptionEditText.value.toString(),
//                    users = mapOf(authRepository.getUserUid() to User("Karol", "", UserStatus.BUSY))
//                )
//            )
//            _isLoading.value = false
//        }
    }

    fun generateGroupCode() {
//        var groupCode = ""
//        launchCatching(invokeOnCompletion = {
//            createGroup(groupCode)
//        }) {
//            _isLoading.value = true
//            var generatedCode = generateCodeSixDigit()
//            groupCode = generatedCode
//            while (statusRepository.isGroupExist(generatedCode)) {
//                generatedCode = generateCodeSixDigit()
//                groupCode = generatedCode
//            }
//        }
    }

    private fun generateCodeSixDigit(): String {
        return (100000 + Random.nextInt(900000)).toString()
    }

    fun onGroupNameChange(editable: Editable) {
        groupNameEditText.value = editable.toString()
        createButtonEnabled.value = editable.toString().length > 5
    }

    fun onGroupDescriptionChange(editable: Editable) {
        groupDescriptionEditText.value = editable.toString()
    }
}
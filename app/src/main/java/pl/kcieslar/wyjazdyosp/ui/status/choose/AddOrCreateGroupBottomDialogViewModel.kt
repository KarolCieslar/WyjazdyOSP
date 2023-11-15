package pl.kcieslar.wyjazdyosp.ui.status.choose

import pl.kcieslar.wyjazdyosp.base.BaseViewModel

class AddOrCreateGroupBottomDialogViewModel() : BaseViewModel() {

    fun navigateToJoinGroupDialog() {
        navigate(AddOrCreateGroupBottomDialogDirections.actionToJoinGroupFragment())
    }

    fun navigateToCreateGroupDialog() {
        navigate(AddOrCreateGroupBottomDialogDirections.actionToCreateGroupBottomDialog())
    }
}
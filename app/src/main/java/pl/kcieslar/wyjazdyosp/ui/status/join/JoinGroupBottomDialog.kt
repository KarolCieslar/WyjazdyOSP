package pl.kcieslar.wyjazdyosp.ui.status.join

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseBottomSheetDialog
import pl.kcieslar.wyjazdyosp.databinding.BottomDialogJoinGroupBinding
import pl.kcieslar.wyjazdyosp.utils.observeNonNull


@AndroidEntryPoint
class JoinGroupBottomDialog : BaseBottomSheetDialog<BottomDialogJoinGroupBinding, JoinGroupBottomDialogViewModel>() {

    override val layoutId: Int = R.layout.bottom_dialog_join_group
    override val viewModel: JoinGroupBottomDialogViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

        viewModel.viewModelEvents.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    is JoinGroupBottomDialogViewModel.EditTextChanged -> {
                        when (event.index) {
                            1 -> binding.joinGroupCodeInput2.requestFocus()
                            2 -> binding.joinGroupCodeInput3.requestFocus()
                            3 -> binding.joinGroupCodeInput4.requestFocus()
                            4 -> binding.joinGroupCodeInput5.requestFocus()
                            5 -> binding.joinGroupCodeInput6.requestFocus()
                        }
                    }
                }
            }
        }
    }
}
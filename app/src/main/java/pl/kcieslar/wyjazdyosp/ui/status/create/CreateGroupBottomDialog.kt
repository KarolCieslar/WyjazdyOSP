package pl.kcieslar.wyjazdyosp.ui.status.create

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseBottomSheetDialog
import pl.kcieslar.wyjazdyosp.databinding.BottomDialogCreateGroupBinding

@AndroidEntryPoint
class CreateGroupBottomDialog : BaseBottomSheetDialog<BottomDialogCreateGroupBinding, CreateGroupBottomDialogViewModel>() {

    override val layoutId: Int = R.layout.bottom_dialog_create_group
    override val viewModel: CreateGroupBottomDialogViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

    }
}
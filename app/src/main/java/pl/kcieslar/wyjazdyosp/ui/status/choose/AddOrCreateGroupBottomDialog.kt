package pl.kcieslar.wyjazdyosp.ui.status.choose

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseBottomSheetDialog
import pl.kcieslar.wyjazdyosp.databinding.BottomDialogAddOrCreateGroupBinding

@AndroidEntryPoint
class AddOrCreateGroupBottomDialog : BaseBottomSheetDialog<BottomDialogAddOrCreateGroupBinding, AddOrCreateGroupBottomDialogViewModel>() {

    override val layoutId: Int = R.layout.bottom_dialog_add_or_create_group
    override val viewModel: AddOrCreateGroupBottomDialogViewModel by viewModels()


    override fun onReady(savedInstanceState: Bundle?) {
    }
}
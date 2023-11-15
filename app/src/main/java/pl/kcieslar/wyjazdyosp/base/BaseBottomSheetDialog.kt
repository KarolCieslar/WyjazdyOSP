package pl.kcieslar.wyjazdyosp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.utils.NavigationCommand
import pl.kcieslar.wyjazdyosp.utils.observeNonNull
import pl.kcieslar.wyjazdyosp.utils.showSnackBar

abstract class BaseBottomSheetDialog<BINDING : ViewDataBinding, VM : BaseViewModel> : BottomSheetDialogFragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract val viewModel: VM

    protected lateinit var binding: BINDING

    protected abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            container,
            false
        )

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(1, viewModel)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
        observeMessage()

        onReady(savedInstanceState)
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun observeMessage() {
        viewModel.message.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { text ->
                showSnackBar(text)
            }
        }
        viewModel.messageRes.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { textRes ->
                showSnackBar(requireContext().getString(textRes))
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

}

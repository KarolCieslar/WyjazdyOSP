package pl.kcieslar.leocrm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.kcieslar.leocrm.utils.NavigationCommand
import pl.kcieslar.leocrm.BR
import pl.kcieslar.leocrm.utils.observeNonNull
import pl.kcieslar.leocrm.utils.showSnackBar

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel>() : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract val viewModel: VM

    protected lateinit var binding: BINDING

    protected abstract fun onReady(savedInstanceState: Bundle?)

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
            setVariable(BR.viewModel, viewModel)
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

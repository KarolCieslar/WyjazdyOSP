package pl.kcieslar.wyjazdyosp.ui.auth

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseFragment
import pl.kcieslar.wyjazdyosp.databinding.FragmentResetPasswordBinding
import pl.kcieslar.wyjazdyosp.utils.observeNonNull

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding, AuthViewModel>() {

    override val layoutId: Int = R.layout.fragment_reset_password
    override val viewModel: AuthViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        viewModel.viewModelEvents.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    is AuthViewModel.ResetPasswordSend -> {
                        showErrorText("Wiadomość z linkiem resetującym hasło został wysłany na podany adres email.", R.color.green)
                        binding.resetPasswordButton.setProgressBar(false)
                        findNavController().navigate(RegisterFragmentDirections.actionGlobalToLoginFragment())
                    }

                    is AuthViewModel.OperationError -> {
                        binding.resetPasswordButton.setProgressBar(false)
                        showErrorText(event.error)
                    }
                }
            }
        }

        binding.resetPasswordButton.setOnClickListener {
            if (isFormValid()) {
                binding.resetPasswordButton.setProgressBar(true)
                binding.errorText.isVisible = false
                viewModel.sendPasswordResetEmail(binding.etMail.text.toString())
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionGlobalToLoginFragment())
        }
    }

    private fun showErrorText(errorMessage: String, color: Int = R.color.red) {
        binding.errorText.apply {
            isVisible = true
            text = errorMessage
            setTextColor(ContextCompat.getColor(context, color))
        }
    }

    private fun isFormValid(): Boolean {
        return if (binding.etMail.text.isNullOrEmpty()) {
            showErrorText(getString(R.string.fields_are_empty))
            false
        } else {
            true
        }
    }
}
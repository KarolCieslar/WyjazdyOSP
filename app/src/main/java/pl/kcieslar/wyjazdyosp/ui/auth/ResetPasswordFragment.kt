package pl.kcieslar.wyjazdyosp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentResetPasswordBinding

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.viewModelEvents.observe(viewLifecycleOwner) { event ->
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
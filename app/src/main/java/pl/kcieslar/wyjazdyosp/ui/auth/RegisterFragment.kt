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
import pl.kcieslar.wyjazdyosp.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.viewModelEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is AuthViewModel.RegisterSuccessful -> {
                    binding.registerButton.setProgressBar(false)
                    binding.etMail.text?.clear()
                    binding.etPassword.text?.clear()
                    binding.etPasswordRepeat.text?.clear()
                    showErrorText("Konto zostało pomyślnie założone. Przejdź do ekranu logowania aby się zalogować.", R.color.green)
                }

                is AuthViewModel.OperationError -> {
                    binding.registerButton.setProgressBar(false)
                    showErrorText(event.error)
                }
            }
        }

        binding.registerButton.setOnClickListener {
            if (isFormValid()) {
                binding.registerButton.setProgressBar(true)
                binding.errorText.isVisible = false
                viewModel.register(
                    binding.etMail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }

        binding.goToLoginFragment.setOnClickListener {
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
        return if (binding.etMail.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty() || binding.etPasswordRepeat.text.isNullOrEmpty()) {
            showErrorText(getString(R.string.fields_are_empty))
            false
        } else if (binding.etPassword.text.toString() != binding.etPasswordRepeat.text.toString()) {
            showErrorText(getString(R.string.passwords_are_not_the_same))
            false
        } else {
            true
        }
    }
}
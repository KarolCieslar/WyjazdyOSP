package pl.kcieslar.wyjazdyosp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCurrentUser()?.let {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToActionListFragment())
            FirebaseCrashlytics.getInstance().setUserId("${it.uid} - ${it.email}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.viewModelEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is AuthViewModel.LoginSuccessful -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToActionListFragment())
                }

                is AuthViewModel.OperationError -> {
                    binding.loginButton.setProgressBar(false)
                    showErrorText(event.error)
                }
            }
        }

        binding.loginButton.setOnClickListener {
            if (isFormValid()) {
                binding.loginButton.setProgressBar(true)
                binding.errorText.isVisible = false
                viewModel.login(
                    binding.etMail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }

        binding.registerAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun showErrorText(errorMessage: String) {
        binding.errorText.apply {
            isVisible = true
            text = errorMessage
        }
    }

    private fun isFormValid(): Boolean {
        return if (binding.etMail.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty()) {
            showErrorText(getString(R.string.fields_are_empty))
            false
        } else {
            true
        }
    }
}
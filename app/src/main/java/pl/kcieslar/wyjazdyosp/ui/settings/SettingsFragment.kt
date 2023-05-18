package pl.kcieslar.wyjazdyosp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.databinding.FragmentSettingsBinding
import pl.kcieslar.wyjazdyosp.ui.auth.AuthViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.sendMailButton.setClickListener { sendEmail() }
        setMenuVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(SettingsFragmentDirections.actionGlobalToLoginFragment())
        }
    }

    private fun sendEmail() {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("karol.cieslarr@gmail.com"))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Wyjazdy OSP - Formularz kontaktowy")
        mIntent.putExtra(Intent.EXTRA_TEXT, "Mam problem z: ")
        mIntent.type = "message/rfc822"

        if (mIntent.resolveActivity(requireContext().packageManager) != null) {
            ContextCompat.startActivity(requireContext(), mIntent, null)
        } else {
            Toast.makeText(context, "Brak aplikacji do obsługi poczty!", Toast.LENGTH_LONG).show()
        }
    }
}
package pl.kcieslar.wyjazdyosp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseFragment
import pl.kcieslar.wyjazdyosp.databinding.FragmentSettingsBinding
import pl.kcieslar.wyjazdyosp.ui.auth.AuthViewModel

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, AuthViewModel>() {

    override val layoutId: Int = R.layout.fragment_settings
    override val viewModel: AuthViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        binding.sendMailButton.setClickListener { sendEmail() }
        setMenuVisibility(false)
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
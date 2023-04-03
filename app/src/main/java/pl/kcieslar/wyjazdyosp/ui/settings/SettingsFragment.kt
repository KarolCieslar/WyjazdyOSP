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
import pl.kcieslar.wyjazdyosp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.sendMailButton.setClickListener { sendEmail() }
        setHasOptionsMenu(false)
        setMenuVisibility(false)
        return binding.root
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
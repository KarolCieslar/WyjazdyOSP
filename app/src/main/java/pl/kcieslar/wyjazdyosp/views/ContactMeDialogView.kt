package pl.kcieslar.wyjazdyosp.views

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import pl.kcieslar.wyjazdyosp.databinding.ViewContactMeDialogBinding

class ContactMeDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewContactMeDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.cancelButton.setOnClickListener { dialog.dismiss() }
        binding.primaryButton.setOnClickListener { sendEmail() }
        dialog.show()
    }

    private fun sendEmail() {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("karol.cieslarr@gmail.com"))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Wyjazdy OSP - Formularz kontaktowy")
        mIntent.putExtra(Intent.EXTRA_TEXT, "Mam problem z: ")
        mIntent.type = "message/rfc822"

        if (mIntent.resolveActivity(context.packageManager) != null) {
            startActivity(context, mIntent, null)
        } else {
            Toast.makeText(context, "Brak aplikacji do obsługi poczty!", Toast.LENGTH_LONG).show()
        }

    }
}
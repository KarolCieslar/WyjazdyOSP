package pl.globoox.ospreportv3.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import pl.globoox.ospreportv3.databinding.ViewConfirmDialogBinding


class ConfirmDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewConfirmDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.cancelButton.setClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setDescription(text: String) {
        binding.description.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
    }

    fun setOnPrimaryButtonClickListener(action: (() -> Unit)) {
        binding.primaryButton.setClickListener {
            dialog.dismiss()
            action()
        }
    }
}
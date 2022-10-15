package pl.kcieslar.wyjazdyosp.views

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
import kcieslar.wyjazdyosp.databinding.ViewHelpDialogBinding


class HelpDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewHelpDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setDescription(text: String) {
        binding.description.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
    }

}

enum class HelpDialogStringRes {
    ACTION_LIST, SALARY, FORCES, ADD_ACTION_STEP_ONE, ADD_ACTION_STEP_SECOND, ADD_ACTION_STEP_THIRD
}
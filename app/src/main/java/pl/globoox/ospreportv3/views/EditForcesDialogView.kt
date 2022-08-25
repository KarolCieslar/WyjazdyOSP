package pl.globoox.ospreportv3.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ViewAddForcesDialogBinding
import pl.globoox.ospreportv3.databinding.ViewEditForcesDialogBinding


class EditForcesDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewEditForcesDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 60)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.cancelButton.apply {
            setText(resources.getString(R.string.button_cancel))
            setClickListener { dialog.dismiss() }
        }
        binding.primaryButton.setText(resources.getString(R.string.button_save))
        dialog.show()
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setDescription(text: String) {
        binding.description.text = text
    }

    fun setContent(text: String) {
        binding.editText.setText(text)
    }

    fun setOnPrimaryButtonClickListener(action: ((editTextField: String) -> Unit)) {
        binding.primaryButton.setClickListener {
            dialog.dismiss()
            action(binding.editText.text.toString())
        }
    }
}
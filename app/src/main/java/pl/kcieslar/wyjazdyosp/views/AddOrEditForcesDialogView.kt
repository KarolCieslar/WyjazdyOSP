package pl.kcieslar.wyjazdyosp.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import pl.kcieslar.wyjazdyosp.databinding.ViewAddOrEditForcesDialogBinding


class AddOrEditForcesDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewAddOrEditForcesDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.cancelButton.setClickListener { dialog.dismiss() }
        dialog.show()

        setPrimaryButtonEnable(false)
        binding.editText.doAfterTextChanged {
            setPrimaryButtonEnable(it.toString().length > 3)
        }
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setError(error: String) {
        binding.editText.error = error
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun setOnPrimaryButtonClickListener(action: ((editTextField: String) -> Unit)) {
        binding.primaryButton.setClickListener {
            action(binding.editText.text.toString())
        }
    }

    fun setPrimaryButtonText(text: String) {
        binding.primaryButton.setText(text)
    }

    fun setEditTextValue(text: String) {
        binding.editText.setText(text)
        setPrimaryButtonEnable(true)
    }

    private fun setPrimaryButtonEnable(enable: Boolean) {
        binding.primaryButton.apply {
            alpha = if (enable) 1f else 0.5f
            setPrimaryButtonEnable(enable)
        }
    }
}
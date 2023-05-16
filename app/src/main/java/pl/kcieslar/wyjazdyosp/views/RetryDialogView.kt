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
import androidx.core.view.isVisible
import pl.kcieslar.wyjazdyosp.databinding.ViewRetryDialogBinding

class RetryDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var dialog: Dialog
    private val binding = ViewRetryDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)
        dialog.setCancelable(false)
        binding.cancelButton.setClickListener { dialog.dismiss() }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun show() {
        binding.viewGroup.isVisible = false
        binding.progressBar.isVisible = true
        dialog.show()
    }

    fun setRetryButtonAction(action: (() -> Unit)) {
        binding.viewGroup.isVisible = true
        binding.progressBar.isVisible = false
        binding.primaryButton.setClickListener {
            action()
        }
    }
}
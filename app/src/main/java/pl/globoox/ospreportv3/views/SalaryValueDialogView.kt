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
import pl.globoox.ospreportv3.databinding.ViewSalaryValueDialogBinding


class SalaryValueDialogView(
    context: Context,
    currentValue: Int,
) : FrameLayout(context) {

    private var dialog: Dialog
    private val binding = ViewSalaryValueDialogBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        dialog.window!!.setBackgroundDrawable(inset)

        binding.numberPicker.maxValue = 50
        binding.numberPicker.minValue = 0
        binding.numberPicker.value = currentValue
        binding.cancelButton.setClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun setOnPrimaryButtonClickListener(action: ((selectedNumber: Int) -> Unit)) {
        binding.primaryButton.setClickListener {
            dialog.dismiss()
            action(binding.numberPicker.value)
        }
    }
}
package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import pl.globoox.ospreportv3.databinding.ViewCancelButtonBinding

class CancelButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewCancelButtonBinding.inflate(LayoutInflater.from(context), this, true)

    fun setText(text: String) {
        binding.button.text = text
    }

    fun setClickListener(action: (() -> Unit)) {
        binding.button.setOnClickListener { action() }
    }
}
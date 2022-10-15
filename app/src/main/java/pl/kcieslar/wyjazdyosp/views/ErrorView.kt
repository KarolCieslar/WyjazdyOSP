package pl.kcieslar.wyjazdyosp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import pl.kcieslar.wyjazdyosp.databinding.ViewErrorViewBinding

class ErrorView @JvmOverloads constructor (
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewErrorViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setMainText(text: String) {
        binding.mainText.text = text
    }

    fun setDescription(text: String) {
        binding.description.text = text
    }

    fun setButtonData(text: String, action: (() -> Unit)) {
        binding.button.apply {
            isVisible = true
            setText(text)
            setClickListener(action)
        }
    }
}
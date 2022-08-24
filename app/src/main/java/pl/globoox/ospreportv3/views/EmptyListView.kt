package pl.globoox.ospreportv3.views

import android.content.ClipDescription
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import pl.globoox.ospreportv3.databinding.ViewEmptyListBinding

class EmptyListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewEmptyListBinding.inflate(LayoutInflater.from(context), this, true)

    fun setMainText(text: String) {
        binding.mainText.text = text
    }

    fun setDescription(text: String) {
        binding.description.text = text
    }

    fun setButtonData(text: String, action: (() -> Unit)) {
        binding.button.apply {
            setText(text)
            setOnClickListener { action }
        }
    }
}
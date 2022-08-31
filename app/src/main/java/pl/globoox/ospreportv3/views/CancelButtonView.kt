package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ViewCancelButtonBinding

class CancelButtonView : FrameLayout {

    private val binding = ViewCancelButtonBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PrimaryButtonView, 0, 0
        )
        val buttonText: CharSequence? = a.getString(R.styleable.PrimaryButtonView_custom_buttonText)

        binding.button.text = buttonText
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setText(text: String) {
        binding.button.text = text
    }

    fun setClickListener(action: (() -> Unit)) {
        binding.button.setOnClickListener { action() }
    }
}
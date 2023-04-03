package pl.kcieslar.wyjazdyosp.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.ViewDateTimeFormFieldBinding

class DateTimeFormFieldView : FrameLayout {

    private val binding = ViewDateTimeFormFieldBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DateTimeFormFieldView, 0, 0
        )
        val textLabel: CharSequence? = a.getString(R.styleable.DateTimeFormFieldView_custom_labelText)
        val icon: Drawable? = a.getDrawable(R.styleable.DateTimeFormFieldView_custom_iconDrawable)

        binding.label.text = textLabel
        binding.label.isVisible = textLabel != null
        binding.icon.setImageDrawable(icon)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setValue(text: String) {
        binding.value.text = text
    }

    fun getValue() : String {
       return binding.value.text.toString()
    }

    fun showLabel() {
       binding.label.isVisible = true
    }
}
package pl.kcieslar.wyjazdyosp.utils

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun setIsInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}
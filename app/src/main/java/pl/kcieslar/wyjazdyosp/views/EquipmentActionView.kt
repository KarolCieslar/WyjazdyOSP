package pl.kcieslar.wyjazdyosp.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import pl.kcieslar.wyjazdyosp.databinding.ViewEquipmentActionBinding

class EquipmentActionView (
    context: Context,
    name: String
) : FrameLayout(context) {

    private val binding = ViewEquipmentActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.name.text = name
    }
}

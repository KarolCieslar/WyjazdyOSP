package pl.globoox.ospreportv3.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import pl.globoox.ospreportv3.databinding.ViewEquipmentActionBinding

class EquipmentActionView (
    context: Context,
    name: String
) : FrameLayout(context) {

    private val binding = ViewEquipmentActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.name.text = name
    }
}

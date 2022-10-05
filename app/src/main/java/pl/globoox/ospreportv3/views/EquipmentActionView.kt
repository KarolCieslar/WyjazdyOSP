package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ViewCarInActionBinding
import pl.globoox.ospreportv3.databinding.ViewEquipmentActionBinding
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.list.FiremansInActionAdapter

class EquipmentActionView (
    context: Context,
    name: String
) : FrameLayout(context) {

    private val binding = ViewEquipmentActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.name.text = name
    }
}

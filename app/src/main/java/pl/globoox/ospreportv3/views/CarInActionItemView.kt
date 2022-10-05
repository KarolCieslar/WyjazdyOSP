package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ViewCarInActionBinding
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.list.FiremansInActionAdapter

class CarInActionItemView (
    context: Context,
    carName: String,
    firemanList: List<Fireman>,
    carAdapterPosition: Int,
) : FrameLayout(context) {

    private val binding = ViewCarInActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.carName.text = carName
        val adapter = FiremansInActionAdapter(carAdapterPosition)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setList(firemanList)
    }
}

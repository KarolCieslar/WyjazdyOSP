package pl.globoox.ospreportv3.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.databinding.ViewCarInActionBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.list.FiremansInActionAdapter

class CarInActionItemView(
    context: Context,
    car: Car,
    firemanList: List<Fireman>,
    isLastItem: Boolean,
) : FrameLayout(context) {

    private val binding = ViewCarInActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.carName.text = car.name
        val adapter = FiremansInActionAdapter(car.id)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setList(firemanList)
        if (isLastItem) binding.recyclerView.setPadding(0, 0, 0, 0)
    }
}

package pl.kcieslar.wyjazdyosp.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import pl.kcieslar.wyjazdyosp.databinding.ViewCarInActionBinding
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.ui.action.list.FiremansInActionAdapter

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

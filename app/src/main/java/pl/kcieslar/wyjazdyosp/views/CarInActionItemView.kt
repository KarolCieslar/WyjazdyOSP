package pl.kcieslar.wyjazdyosp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import pl.kcieslar.wyjazdyosp.databinding.ViewCarInActionBinding
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.ui.action.list.FiremansInActionAdapter

class CarInActionItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding = ViewCarInActionBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(
        context: Context,
        car: Car,
        firemanList: List<Fireman>,
        isLastItem: Boolean,
    ) {
        binding.carName.text = car.name
        val adapter = FiremansInActionAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setList(firemanList)
        if (isLastItem) binding.recyclerView.setPadding(0, 0, 0, 0)
    }
}

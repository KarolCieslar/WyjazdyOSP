package pl.globoox.ospreportv3.ui.forces.cars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemForcesCarListBinding
import pl.globoox.ospreportv3.model.Car

class CarListAdapter(
    val onItemClick: ((car: Car) -> Unit),
    val onRemoveClick: ((car: Car) -> Unit)
) : RecyclerView.Adapter<CarListAdapter.MyViewHolder>() {

    private var carList: List<Car> = emptyList()

    inner class MyViewHolder(val binding: ItemForcesCarListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesCarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(carList[position]){
                binding.name.text = this.name
                binding.item.setOnClickListener {
                    onItemClick(this)
                }
                binding.removeIcon.setOnClickListener {
                    onRemoveClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    fun setData(carList: List<Car>) {
        this.carList = carList
        notifyDataSetChanged()
    }
}
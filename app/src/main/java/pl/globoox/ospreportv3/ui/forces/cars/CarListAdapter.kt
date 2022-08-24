package pl.globoox.ospreportv3.ui.forces.cars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemFiremanMainListBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman

class CarListAdapter : RecyclerView.Adapter<CarListAdapter.MyViewHolder>() {

    private var carList: List<Car> = emptyList()

    inner class MyViewHolder(val binding: ItemFiremanMainListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFiremanMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(carList[position]){
                binding.position.text = position.toString()
                binding.name.text = this.name
                binding.item.setOnClickListener {
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
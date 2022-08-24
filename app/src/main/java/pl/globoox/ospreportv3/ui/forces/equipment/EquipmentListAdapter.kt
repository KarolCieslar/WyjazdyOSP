package pl.globoox.ospreportv3.ui.forces.equipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemFiremanMainListBinding
import pl.globoox.ospreportv3.model.Fireman

class EquipmentListAdapter : RecyclerView.Adapter<EquipmentListAdapter.MyViewHolder>() {

    private var equipmentList: List<Fireman> = emptyList()

    inner class MyViewHolder(val binding: ItemFiremanMainListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFiremanMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(equipmentList[position]){
                binding.position.text = position.toString()
                binding.name.text = this.firstName
                binding.surname.text = this.lastName
                binding.item.setOnClickListener {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    fun setData(firemanList: List<Fireman>) {
        this.equipmentList = firemanList
        notifyDataSetChanged()
    }
}
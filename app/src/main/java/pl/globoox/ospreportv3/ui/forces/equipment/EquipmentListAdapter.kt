package pl.globoox.ospreportv3.ui.forces.equipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemForcesEquipmentListBinding
import pl.globoox.ospreportv3.model.Equipment

class EquipmentListAdapter : RecyclerView.Adapter<EquipmentListAdapter.MyViewHolder>() {

    private var equipmentList: List<Equipment> = emptyList()

    inner class MyViewHolder(val binding: ItemForcesEquipmentListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesEquipmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(equipmentList[position]){
                binding.name.text = this.name
                binding.item.setOnClickListener {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    fun setData(equipmentList: List<Equipment>) {
        this.equipmentList = equipmentList
        notifyDataSetChanged()
    }
}
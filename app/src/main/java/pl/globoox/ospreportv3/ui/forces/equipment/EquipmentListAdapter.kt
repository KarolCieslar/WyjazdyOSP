package pl.globoox.ospreportv3.ui.forces.equipment

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesEquipmentListBinding
import pl.globoox.ospreportv3.model.Equipment

class EquipmentListAdapter(
    val onItemClick: ((equipment: Equipment) -> Unit),
    val onRemoveClick: ((equipment: Equipment) -> Unit),
    val onEditClick: ((equipment: Equipment) -> Unit)
) : RecyclerView.Adapter<EquipmentListAdapter.MyViewHolder>() {

    private var equipmentList: List<Equipment> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesEquipmentListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesEquipmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(equipmentList[position]){
                binding.name.text = this.name
                binding.item.setOnClickListener {
                    onItemClick(this)
                }

                binding.optionsIcon.setOnClickListener {
                    val popupMenu = PopupMenu(context, binding.optionsIcon)
                    popupMenu.gravity = Gravity.END;
                    popupMenu.menuInflater.inflate(R.menu.popup_forces_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove ->
                                onRemoveClick(this)
                            R.id.edit ->
                                onEditClick(this)
                        }
                        true
                    }
                    popupMenu.show()
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
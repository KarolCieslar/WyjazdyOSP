package pl.globoox.ospreportv3.ui.action.add.stepSecond

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemStepSecondCarBinding
import pl.globoox.ospreportv3.databinding.ItemStepSecondEquipmentBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman
import java.lang.IllegalArgumentException


class StepSecondAdapter(val onItemClick: ((fireman: Fireman) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: List<Any> = emptyList()
    private var selectedItemList: MutableList<Any> = mutableListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when (viewType) {
            ViewType.EQUIPMENT -> {
                val binding = ItemStepSecondEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EquipmentElementViewHolder(binding)
            }
            ViewType.CAR -> {
                val binding = ItemStepSecondCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CarElementViewHolder(binding)
            }
            else -> throw IllegalArgumentException("viewType not exists")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.EQUIPMENT -> {
                (holder as EquipmentElementViewHolder).bind(itemList[position] as Equipment)
            }
            ViewType.CAR -> {
                (holder as CarElementViewHolder).bind(itemList[position] as Car)
            }
        }
    }

    inner class CarElementViewHolder(private val binding: ItemStepSecondCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.name.text = car.name
            binding.item.setOnClickListener {
                handleItemClick(it, car)
            }
        }
    }

    inner class EquipmentElementViewHolder(private val binding: ItemStepSecondEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: Equipment) {
            binding.name.text = equipment.name
            binding.item.setOnClickListener {
                handleItemClick(it, equipment)
            }
        }
    }

    private fun handleItemClick(view: View, item: Any) {
        if (selectedItemList.contains(item)) {
            selectedItemList.remove(item)
            view.backgroundTintList = (ContextCompat.getColorStateList(context, R.color.white))
        } else {
            selectedItemList.add(item)
            view.backgroundTintList = (ContextCompat.getColorStateList(context, R.color.yellow))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getSelectedItems(): List<Any> {
        return selectedItemList
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Car -> ViewType.CAR
            is Equipment -> ViewType.EQUIPMENT
            else -> throw IllegalArgumentException("getItemViewType not exists")
        }
    }

    fun addData(list: List<Any>) {
        this.itemList = (itemList + list).sortedBy { it is Equipment }
        notifyDataSetChanged()
    }

    private object ViewType {
        const val CAR = 0
        const val EQUIPMENT = 1
    }
}
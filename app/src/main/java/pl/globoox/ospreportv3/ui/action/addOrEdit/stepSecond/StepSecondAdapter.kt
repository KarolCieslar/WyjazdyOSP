package pl.globoox.ospreportv3.ui.action.addOrEdit.stepSecond

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemStepSecondCarBinding
import pl.globoox.ospreportv3.databinding.ItemStepSecondEquipmentBinding
import pl.globoox.ospreportv3.model.*
import java.lang.IllegalArgumentException


class StepSecondAdapter(
    val action: Action
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: MutableList<Forces> = mutableListOf()
    private var selectedItemList: MutableList<Forces> = mutableListOf()
    private lateinit var context: Context

    init {
        selectedItemList.addAll(action.equipment)
        selectedItemList.addAll(action.carsInAction.map { it.car })
    }

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
                handleItemClick(binding.item, binding.selectedIcon, car)
            }
            setSelectedStatus(binding.item, binding.selectedIcon, car)
        }
    }

    inner class EquipmentElementViewHolder(private val binding: ItemStepSecondEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: Equipment) {
            binding.name.text = equipment.name
            binding.item.setOnClickListener {
                handleItemClick(binding.item, binding.selectedIcon, equipment)
            }
            setSelectedStatus(binding.item, binding.selectedIcon, equipment)
        }
    }

    private fun handleItemClick(view: View, selectedIcon: ImageView, item: Forces) {
        if (selectedItemList.contains(item)) {
            selectedItemList.remove(item)
            view.setBackgroundColor(0)
            selectedIcon.isVisible = false
        } else {
            selectedItemList.add(item)
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
            selectedIcon.isVisible = true
        }
    }

    private fun setSelectedStatus(view: View, selectedIcon: ImageView, item: Forces) {
        if (selectedItemList.contains(item)) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
            selectedIcon.isVisible = true
        } else {
            view.setBackgroundColor(0)
            selectedIcon.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getSelectedItems(): List<Forces> {
        return selectedItemList
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Car -> ViewType.CAR
            is Equipment -> ViewType.EQUIPMENT
            else -> throw IllegalArgumentException("getItemViewType not exists")
        }
    }

    fun setData(list: MutableList<Forces>) {
        this.itemList = list.sortedBy { it is Equipment }.toMutableList()
        action.equipment.forEach { if(!itemList.contains(it)) itemList.add(it) }
        action.carsInAction.map { it.car }.forEach { if(!itemList.contains(it)) itemList.add(it) }
        itemList.sortBy { it is Equipment }
        notifyDataSetChanged()
    }

    private object ViewType {
        const val CAR = 0
        const val EQUIPMENT = 1
    }
}
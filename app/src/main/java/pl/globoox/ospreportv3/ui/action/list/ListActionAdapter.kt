package pl.globoox.ospreportv3.ui.action.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemActionListBinding
import pl.globoox.ospreportv3.databinding.ItemSalaryFiremanBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.forces.view.ViewPagerListAdapter
import pl.globoox.ospreportv3.utils.durationFormatter
import pl.globoox.ospreportv3.views.CarInActionItemView
import pl.globoox.ospreportv3.views.EquipmentActionView


class ListActionAdapter(
    val onEditButtonClick: ((action: Action) -> Unit),
    val onRemoveButtonClick: ((action: Action) -> Unit)
) : RecyclerView.Adapter<ListActionAdapter.MyViewHolder>() {

    private var itemList: MutableList<Action> = mutableListOf()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemActionListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemActionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(itemList[position]) {
                val action = this

                binding.item.setOnClickListener {
                    binding.moreInfoView.isVisible = !binding.moreInfoView.isVisible
                }

                binding.number.text = action.number
                binding.autoincrement.text = "#${action.id}"
                binding.location.text = action.location

                val diffText = durationFormatter(action.outTime, action.inTime)
                binding.time.text = "${action.getFormattedOutTime()} - ${action.getFormattedInTime()}"
                binding.actionTime.text = "Czas trwania: ${diffText}"

                binding.crevList.removeAllViews()
                action.carsInAction.forEachIndexed { index, carInAction ->
                    binding.crevList.addView(CarInActionItemView(context, carInAction.car, carInAction.firemans, index == action.carsInAction.size - 1))
                }

                binding.equpmentList.removeAllViews()
                binding.equipmentLabel.isVisible = action.equipment.isNotEmpty()
                action.equipment.forEach { equipment ->
                    binding.equpmentList.addView(EquipmentActionView(context, equipment.name))
                }

                binding.descriptionLabel.isVisible = !action.description.isNullOrBlank()
                binding.description.isVisible = !action.description.isNullOrBlank()
                binding.description.text = action.description

                binding.editButton.setOnClickListener {
                    onEditButtonClick(action)
                }
                binding.removeButton.setOnClickListener {
                    onRemoveButtonClick(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(newItemList: List<Action>) {
        val diffCallback = DiffUtils(itemList, newItemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemList.clear()
        itemList.addAll(newItemList)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffUtils(private val oldList: List<Action>, private val newList: List<Action>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition] && oldItemPosition == newItemPosition
        }
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    }
}
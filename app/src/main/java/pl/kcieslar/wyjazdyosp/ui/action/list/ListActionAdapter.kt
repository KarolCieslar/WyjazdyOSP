package pl.kcieslar.wyjazdyosp.ui.action.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import pl.kcieslar.wyjazdyosp.databinding.ItemActionListBinding
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.utils.durationFormatter
import pl.kcieslar.wyjazdyosp.views.CarInActionItemView
import pl.kcieslar.wyjazdyosp.views.EquipmentActionView


class ListActionAdapter(
    val onEditButtonClick: ((action: Action) -> Unit),
    val onRemoveButtonClick: ((action: Action) -> Unit)
) : RecyclerView.Adapter<ListActionAdapter.MyViewHolder>() {

    private var itemList: MutableList<Action> = mutableListOf()
    private var expandedItems: MutableList<Action> = mutableListOf()
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

                binding.moreInfoView.isVisible = expandedItems.contains(action)
                binding.item.setOnClickListener {
                    if (expandedItems.contains(action)) {
                        expandedItems.remove(action)
                    } else {
                        expandedItems.add(action)
                    }
                    binding.moreInfoView.isVisible = !binding.moreInfoView.isVisible
                }

                binding.number.visibility = if (action.number.isNullOrEmpty()) View.GONE else View.VISIBLE
                binding.number.text = action.number
                binding.autoincrement.text = "#${itemList.size - position}"
                binding.location.text = action.location

                val diffText = durationFormatter(action.outTime, action.inTime)
                binding.time.text = "${action.getFormattedOutTime()} - ${action.getFormattedInTime()}"
                binding.actionTime.text = "Czas trwania: ${diffText}"

                binding.crevList.removeAllViews()
                FirebaseCrashlytics.getInstance().log("Cars in action size: ${action.carsInAction.size}")
                action.carsInAction.forEachIndexed { index, carInAction ->
                    if (carInAction.car != null && carInAction.firemans != null) {
                        val carInActionItemView = CarInActionItemView(context)
                        carInActionItemView.setData(context, carInAction.car, carInAction.firemans, index == action.carsInAction.size - 1)
                        binding.crevList.addView(carInActionItemView)
                    }
                }

                binding.equipmentList.removeAllViews()
                binding.equipmentLabel.isVisible = action.equipment.isNotEmpty()
                action.equipment.forEach { equipment ->
                    binding.equipmentList.addView(EquipmentActionView(context, equipment.name))
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

    fun removeFromExpanded(action: Action) {
        expandedItems.remove(action)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(newItemList: List<Action>) {
        itemList = newItemList.toMutableList()
        notifyDataSetChanged()
//        val diffCallback = DiffUtils(itemList, newItemList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        itemList.clear()
//        itemList.addAll(newItemList)
//        diffResult.dispatchUpdatesTo(this)
    }

//    class DiffUtils(private val oldList: List<Action>, private val newList: List<Action>) : DiffUtil.Callback() {
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldList[oldItemPosition].id == newList[newItemPosition].id
//        }
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return false
//        }
//
//        override fun getOldListSize() = oldList.size
//        override fun getNewListSize() = newList.size
//    }
}
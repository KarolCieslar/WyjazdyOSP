package pl.globoox.ospreportv3.ui.action.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemActionListBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.utils.convertToLocalDateTime
import pl.globoox.ospreportv3.utils.durationFormatter
import pl.globoox.ospreportv3.views.CarInActionItemView


class ListActionAdapter(
    val onItemClick: ((action: Action) -> Unit),
    val recyclerView: RecyclerView
) : RecyclerView.Adapter<ListActionAdapter.ViewHolder>() {

    private var currentExpandedAction = UNSELECTED
    private var itemList: List<Action> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val binding = ItemActionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemActionListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.expandableLayout.setInterpolator(OvershootInterpolator())
            binding.cardView.setOnClickListener(this)
        }

        fun bind() {
            val position = adapterPosition
            val isSelected = position == currentExpandedAction
            val action = itemList[position]

            binding.primaryButton.setOnClickListener {
                onItemClick(action)
            }

            binding.cardView.isSelected = isSelected
            binding.expandableLayout.setExpanded(isSelected, false)
            binding.number.text = action.number
            binding.autoincrement.text = "#${action.id}"
            binding.location.text = action.location

            val diffText = durationFormatter(convertToLocalDateTime(action.outTime), convertToLocalDateTime(action.inTime))
            binding.outTime.text = action.outTime
            binding.inTime.text = action.inTime
            binding.actionTime.text = "(${diffText})"

            action.carsInAction.forEach { carInAction ->
                binding.crevList.addView(CarInActionItemView(context, carInAction.car.name!!, carInAction.firemans))
            }


            binding.description.isVisible = action.description.isNotEmpty()
            binding.description.text = action.description
        }

        override fun onClick(view: View) {
            val holder = recyclerView.findViewHolderForAdapterPosition(currentExpandedAction) as ViewHolder?
            if (holder != null) {
                holder.binding.cardView.isSelected = false
                holder.binding.expandableLayout.collapse()
            }
            val position = adapterPosition
            if (position == currentExpandedAction) {
                currentExpandedAction = UNSELECTED
            } else {
                binding.cardView.isSelected = true
                binding.expandableLayout.expand()
                currentExpandedAction = position
            }
        }
    }

    fun setList(list: List<Action>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    companion object {
        private const val UNSELECTED = -1
    }
}
package pl.globoox.ospreportv3.ui.action.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemActionListBinding
import pl.globoox.ospreportv3.databinding.ItemSalaryFiremanBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.utils.durationFormatter
import pl.globoox.ospreportv3.views.CarInActionItemView


class ListActionAdapter(
    val onEditButtonClick: ((action: Action) -> Unit)
) : RecyclerView.Adapter<ListActionAdapter.MyViewHolder>() {

    private var itemList: List<Action> = emptyList()
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

                binding.number.text = action.number
                binding.autoincrement.text = "#${action.id}"
                binding.location.text = action.location

                val diffText = durationFormatter(action.outTime, action.inTime)
                binding.outTime.text = action.getFormattedOutTime()
                binding.inTime.text = action.getFormattedInTime()
                binding.actionTime.text = "(${diffText})"

                binding.crevList.removeAllViews()
                action.carsInAction.forEachIndexed { index, carInAction ->
                    binding.crevList.addView(CarInActionItemView(context, carInAction.car.name!!, carInAction.firemans, index))
                }

                binding.descriptionLabel.isVisible = !action.description.isNullOrBlank()
                binding.description.isVisible = !action.description.isNullOrBlank()
                binding.description.text = action.description

                binding.primaryButton.setClickListener {
                    onEditButtonClick(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(list: List<Action>) {
        this.itemList = list
        notifyDataSetChanged()
    }
}
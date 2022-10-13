package pl.globoox.ospreportv3.ui.forces.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesListBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.model.Forces

class ViewPagerListAdapter(
    val onItemClick: ((item: Forces) -> Unit),
    val onEditClick: ((item: Forces) -> Unit)
) : RecyclerView.Adapter<ViewPagerListAdapter.MyViewHolder>() {

    private var itemList: MutableList<Forces> = mutableListOf()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(itemList[position]){
                val icon = when (this) {
                    is Fireman -> R.drawable.ic_fireman
                    is Car -> R.drawable.ic_car
                    else -> R.drawable.ic_equipment
                }
                binding.icon.setImageDrawable(ContextCompat.getDrawable(context, icon))
                binding.name.text = this.name
                binding.item.setOnClickListener {
                    onItemClick(this)
                }

                binding.deleteIcon.setOnClickListener {
                    onEditClick(this)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItems(newItemList: List<Forces>) {
        val diffCallback = DiffUtils(itemList, newItemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemList.clear()
        itemList.addAll(newItemList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItems() : List<Forces> {
        return itemList
    }

    class DiffUtils(private val oldList: List<Forces>, private val newList: List<Forces>) : DiffUtil.Callback() {
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
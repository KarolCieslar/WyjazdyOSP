package pl.globoox.ospreportv3.ui.forces.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesListBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Forces

class ViewPagerListAdapter(
    val onItemClick: ((item: Forces) -> Unit),
    val onEditClick: ((item: Forces) -> Unit)
) : RecyclerView.Adapter<ViewPagerListAdapter.MyViewHolder>() {

    private var itemList: List<Forces> = emptyList()
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
                binding.position.text = (position + 1).toString()
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

    fun setItems(itemList: List<Forces>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getItems() : List<Forces> {
        return itemList
    }
}
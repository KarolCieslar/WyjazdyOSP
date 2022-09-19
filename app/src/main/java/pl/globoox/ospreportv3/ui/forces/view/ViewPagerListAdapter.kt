package pl.globoox.ospreportv3.ui.forces.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesCarListBinding
import pl.globoox.ospreportv3.model.Car

class ViewPagerListAdapter(
    val onItemClick: ((item: Any) -> Unit),
    val onEditClick: ((item: Any) -> Unit)
) : RecyclerView.Adapter<ViewPagerListAdapter.MyViewHolder>() {

    private var itemList: List<Any> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesCarListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesCarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(itemList[position]){
//                binding.name.text = this.name
                binding.item.setOnClickListener {
                    onItemClick(this)
                }

                binding.optionsIcon.setOnClickListener {
                    onEditClick(this)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(itemList: List<Any>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}
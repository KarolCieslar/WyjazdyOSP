package pl.kcieslar.wyjazdyosp.ui.forces.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.ItemForcesListBinding
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

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
        with(holder) {
            with(itemList[position]) {
                val icon = when (this.type) {
                    ForcesDataType.FIREMAN -> R.drawable.ic_fireman
                    ForcesDataType.CAR -> R.drawable.ic_car
                    ForcesDataType.EQUIPMENT -> R.drawable.ic_equipment
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
        itemList = newItemList.toMutableList()
        notifyDataSetChanged()
    }

    fun getItems(): List<Forces> {
        return itemList
    }
}
package pl.kcieslar.wyjazdyosp.ui.status.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.kcieslar.wyjazdyosp.databinding.ItemStatusGroupBinding
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Group

class StatusGroupListAdapter : RecyclerView.Adapter<StatusGroupListAdapter.MyViewHolder>() {

    private var itemList: MutableList<Group> = mutableListOf()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemStatusGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStatusGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            val group = itemList[position]
            number.text = group.code
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(newItemList: List<Group>) {
        itemList = newItemList.toMutableList()
        notifyDataSetChanged()
    }
}
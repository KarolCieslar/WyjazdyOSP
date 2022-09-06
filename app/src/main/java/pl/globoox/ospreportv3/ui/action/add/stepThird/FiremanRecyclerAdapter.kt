package pl.globoox.ospreportv3.ui.action.add.stepThird

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemAddActionFiremanBinding
import pl.globoox.ospreportv3.model.Fireman


class FiremanRecyclerAdapter(
    val onItemClick: ((fireman: Fireman) -> Unit),
    val onCheckBoxChange: ((fireman: Fireman, isChecked: Boolean) -> Unit)
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: List<Fireman> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAddActionFiremanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FiremanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FiremanViewHolder).bind(itemList[position])
    }

    inner class FiremanViewHolder(private val binding: ItemAddActionFiremanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fireman: Fireman) {
            Log.d("asasddsa", "FiremanViewHolder")
            binding.name.text = fireman.name
            binding.checkbox.isChecked = fireman.selectStatus != null
            binding.checkbox.setOnClickListener {
                onCheckBoxChange(fireman, binding.checkbox.isChecked)
            }
            binding.item.setOnClickListener { onItemClick(fireman) }

            binding.commanderIcon.isVisible = fireman.functions?.contains(FiremanFunction.COMMANDER) ?: false
            binding.driverIcon.isVisible = fireman.functions?.contains(FiremanFunction.DRIVER) ?: false
            binding.ownCarIcon.isVisible = fireman.functions?.contains(FiremanFunction.OWNCAR) ?: false

            binding.divider.isVisible = position + 1 != itemCount
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(newList: List<Fireman>) {
        this.itemList = newList
        notifyDataSetChanged()
//        val sortedList = newList.sortedWith(compareBy<Fireman>
//        { it.selectStatus == null }
//            .thenBy { it.functions?.contains(FiremanFunction.DRIVER) == false }
//            .thenBy { it.functions?.contains(FiremanFunction.COMMANDER) == false })
//        val diffCallback = FiremanDiff(itemList, sortedList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        itemList = sortedList
//        diffResult.dispatchUpdatesTo(this)
    }

    class FiremanDiff(private val oldList: List<Fireman>, private val newList: List<Fireman>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    }
}
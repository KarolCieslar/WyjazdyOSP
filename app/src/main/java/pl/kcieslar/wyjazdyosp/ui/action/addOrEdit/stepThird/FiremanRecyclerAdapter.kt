package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.ItemAddActionFiremanBinding
import pl.kcieslar.wyjazdyosp.model.Fireman

class FiremanRecyclerAdapter(
    val onCheckBoxChange: ((fireman: Fireman, isChecked: Boolean) -> Unit),
    val onFunctionIconClick: ((fireman: Fireman, firemanFunction: FiremanFunction) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: List<Fireman> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAddActionFiremanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return FiremanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FiremanViewHolder).bind(itemList[position])
    }

    inner class FiremanViewHolder(private val binding: ItemAddActionFiremanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fireman: Fireman) {
            binding.divider.isVisible = itemList.size != adapterPosition + 1
            binding.name.text = fireman.name
            binding.checkbox.isChecked = fireman.selectedCar != null
            binding.checkbox.setOnClickListener {
                onCheckBoxChange(fireman, binding.checkbox.isChecked)
            }
            binding.name.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
                onCheckBoxChange(fireman, binding.checkbox.isChecked)
            }
            binding.commanderIcon.apply {
                setOnClickListener { onFunctionIconClick(fireman, FiremanFunction.COMMANDER) }
                setColorFilter(getIconSelectStatus(fireman, FiremanFunction.COMMANDER))
            }
            binding.driverIcon.apply {
                setOnClickListener { onFunctionIconClick(fireman, FiremanFunction.DRIVER) }
                setColorFilter(getIconSelectStatus(fireman, FiremanFunction.DRIVER))
            }
            binding.ownCarIcon.apply {
                setOnClickListener { onFunctionIconClick(fireman, FiremanFunction.OWNCAR) }
                setColorFilter(getIconSelectStatus(fireman, FiremanFunction.OWNCAR))
            }
        }
    }

    private fun getIconSelectStatus(fireman: Fireman, firemanFunction: FiremanFunction): Int {
        return ContextCompat.getColor(context, if (fireman.getFiremanFunction(firemanFunction)) R.color.black else R.color.black100)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(newList: List<Fireman>) {
        this.itemList = newList
        notifyDataSetChanged()
    }

    class FiremanDiff(private val oldList: List<Fireman>, private val newList: List<Fireman>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].key == newList[newItemPosition].key
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    }
}
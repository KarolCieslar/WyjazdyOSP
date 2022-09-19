package pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemAddActionFiremanBinding
import pl.globoox.ospreportv3.model.Fireman

class FiremanRecyclerAdapter(
    val onCheckBoxChange: ((fireman: Fireman, isChecked: Boolean) -> Unit)
) :
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
            Log.d("asasddsa", "FiremanViewHolder")
            binding.name.text = fireman.name
            binding.checkbox.isChecked = fireman.selectStatus != null
            binding.checkbox.setOnClickListener {
                onCheckBoxChange(fireman, binding.checkbox.isChecked)
            }
            binding.name.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
                onCheckBoxChange(fireman, binding.checkbox.isChecked)
            }
            binding.commanderIcon.apply {
                setOnClickListener { onFunctionIconClick(it as ImageView, fireman, FiremanFunction.COMMANDER) }
                backgroundTintList = getIconColorStateList(fireman, FiremanFunction.COMMANDER)
            }
            binding.driverIcon.apply {
                setOnClickListener { onFunctionIconClick(it as ImageView, fireman, FiremanFunction.DRIVER) }
                backgroundTintList = getIconColorStateList(fireman, FiremanFunction.DRIVER)
            }
            binding.ownCarIcon.apply {
                setOnClickListener { onFunctionIconClick(it as ImageView, fireman, FiremanFunction.OWNCAR) }
                backgroundTintList = getIconColorStateList(fireman, FiremanFunction.OWNCAR)
            }
        }
    }

    private fun onFunctionIconClick(view: ImageView, fireman: Fireman, firemanFunction: FiremanFunction) {
        if (fireman.functions!!.contains(firemanFunction)) {
            view.setColorFilter(ContextCompat.getColor(context, R.color.black100), PorterDuff.Mode.SRC_IN)
            fireman.functions!!.remove(firemanFunction)
        } else {
            view.setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN)
            fireman.functions!!.add(firemanFunction)
        }
        notifyDataSetChanged()
    }

    private fun getIconColorStateList(fireman: Fireman, firemanFunction: FiremanFunction): ColorStateList {
        return ColorStateList.valueOf(ContextCompat.getColor(context, if (fireman.functions?.contains(firemanFunction) == true) R.color.black100 else R.color.black))
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
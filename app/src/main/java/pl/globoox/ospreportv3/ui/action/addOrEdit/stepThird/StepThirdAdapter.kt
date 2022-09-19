package pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.cachapa.expandablelayout.ExpandableLayout
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemAddActionCarBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman


class StepThirdAdapter(
    val recyclerView: RecyclerView
) : RecyclerView.Adapter<StepThirdAdapter.ViewHolder>() {

    private var currentExpandedCar = UNSELECTED
    private var itemList: List<Car> = emptyList()
    private var allFiremansList: List<Fireman> = emptyList()
    private lateinit var context: Context
    private lateinit var firemansAdapter: FiremanRecyclerAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val binding = ItemAddActionCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getFiremans(): List<Fireman> {
        return allFiremansList
    }


    inner class ViewHolder(private val binding: ItemAddActionCarBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

        init {
            binding.expandableLayout.setInterpolator(OvershootInterpolator())
            binding.expandableLayout.setOnExpansionUpdateListener(this)
            binding.item.setOnClickListener(this)
            prepareAdapterAndSetData(binding, adapterPosition)
            binding.firemanRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        fun bind() {
            val position = adapterPosition
            val isSelected = position == currentExpandedCar
            binding.name.text = itemList[position].name
            binding.item.isSelected = isSelected
            binding.expandableLayout.setExpanded(isSelected, false)
            prepareAdapterAndSetData(binding, position)
            if (isSelected) binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            repeat(itemList.size) {
                if (it != position) {
                    val holder = recyclerView.findViewHolderForAdapterPosition(it) as ViewHolder?
                    if (holder != null) {
                        holder.binding.item.isSelected = false
                        holder.binding.expandableLayout.collapse()
                        holder.binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }
                }
            }
            if (position != currentExpandedCar) {
                Log.d("adsadsasd", "2222")
                binding.item.isSelected = true
                binding.expandableLayout.expand()
                currentExpandedCar = position
                binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
            }
            prepareAdapterAndSetData(binding, adapterPosition)
        }


        override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
            Log.d("ExpandableLayout", "State: $state")
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView.smoothScrollToPosition(adapterPosition)
            }
        }
    }

    private fun prepareAdapterAndSetData(binding: ItemAddActionCarBinding, position: Int) {
        firemansAdapter = FiremanRecyclerAdapter(
            onCheckBoxChange = { fireman, isChecked -> changeFiremanSelectStatus(fireman, position, isChecked) }
        )
        binding.firemanRecyclerView.adapter = firemansAdapter
        val filteredItems = getFilteredFiremans(position)
        binding.emptyView.isVisible = filteredItems.isEmpty()
        firemansAdapter.setData(filteredItems)
    }

    private fun changeFiremanSelectStatus(fireman: Fireman, position: Int, isSelected: Boolean) {
        allFiremansList.first { it == fireman }.selectStatus = if (isSelected) position else null
        firemansAdapter.setData(getFilteredFiremans(position))
    }

    fun setCars(list: List<Car>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun setFiremans(list: List<Fireman>) {
        this.allFiremansList = list.toMutableList()
        notifyDataSetChanged()
    }

    private fun getFilteredFiremans(position: Int): List<Fireman> {
        return allFiremansList.filter { it.selectStatus == null || it.selectStatus == position }
    }

    companion object {
        private const val UNSELECTED = 0
    }
}
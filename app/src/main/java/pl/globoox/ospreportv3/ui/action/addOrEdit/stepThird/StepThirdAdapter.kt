package pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemAddActionCarBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman


class StepThirdAdapter(
    val recyclerView: RecyclerView,
    val action: Action
) : RecyclerView.Adapter<StepThirdAdapter.ViewHolder>() {

    private var currentExpandedCar = UNSELECTED
    private var itemList: List<Car> = emptyList()
    private var allFiremansList: MutableList<Fireman> = mutableListOf()
    private lateinit var context: Context

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


    inner class ViewHolder(private val binding: ItemAddActionCarBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var firemansAdapter: FiremanRecyclerAdapter

        init {
            binding.item.setOnClickListener(this)
            binding.firemanRecyclerView.layoutManager = LinearLayoutManager(context)
            currentExpandedCar = itemList[0].id
        }

        fun bind() {
            val car = itemList[adapterPosition]
            val isSelected = car.id == currentExpandedCar
            binding.name.text = car.name
            prepareAdapterAndSetData(binding, car.id)
            if (isSelected) binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
        }

        private fun prepareAdapterAndSetData(binding: ItemAddActionCarBinding, carId: Int) {
            firemansAdapter = FiremanRecyclerAdapter(
                onCheckBoxChange = { fireman, isChecked -> changeFiremanSelectStatus(fireman, carId, isChecked) },
                onFunctionIconClick = { fireman, function -> changeFiremanFunction(allFiremansList.first { it == fireman }, function, carId) },
                carId = carId
            )
            binding.firemanRecyclerView.adapter = firemansAdapter
            val filteredItems = getFilteredFiremans(carId)
            binding.emptyView.isVisible = filteredItems.isEmpty()
            firemansAdapter.setData(filteredItems)
        }

        private fun changeFiremanSelectStatus(fireman: Fireman, carId: Int, isSelected: Boolean) {
            allFiremansList.first { it == fireman }.selectStatus = if (isSelected) carId else null
            firemansAdapter.setData(getFilteredFiremans(carId))
        }

        private fun changeFiremanFunction(fireman: Fireman, function: FiremanFunction, carId: Int) {
            val filteredFiremans = getFilteredFiremans(carId).toMutableList()
            val firemanFunctions = fireman.functions[carId] ?: mutableListOf()
            if (firemanFunctions.contains(function)) {
                firemanFunctions.remove(function)
            } else {
                if (function != FiremanFunction.OWNCAR) {
                    filteredFiremans.forEach {
                        val thisFiremanFunctions = it.functions[carId] ?: mutableListOf()
                        thisFiremanFunctions.remove(function)
                        it.functions[carId] = thisFiremanFunctions
                    }
                }
                firemanFunctions.add(function)
                fireman.functions[carId] = firemanFunctions
            }
            firemansAdapter.setData(filteredFiremans)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            val carId = itemList[position].id
            repeat(itemList.size) {
                if (carId != it) {
                    val holder = recyclerView.findViewHolderForAdapterPosition(it) as ViewHolder?
                    if (holder != null) {
                        holder.binding.carExpandLayout.isVisible = false
                        holder.binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }
                }
            }
            if (currentExpandedCar == carId) {
                binding.carExpandLayout.isVisible = false
                currentExpandedCar = -1
                binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.carExpandLayout.isVisible = true
                currentExpandedCar = carId
                binding.carItemSection.setBackgroundColor(ContextCompat.getColor(context, R.color.black100))
            }
            prepareAdapterAndSetData(binding, carId)
        }
    }

    fun setCars(list: List<Car>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun setFiremans(list: List<Fireman>) {
        this.allFiremansList = list.toMutableList()
        action.carsInAction.flatMap { it.firemans }.forEach { firemanEditor ->
            if (allFiremansList.firstOrNull { firemanEditor.id == it.id } != null) allFiremansList.removeIf { it.id == firemanEditor.id}
            allFiremansList.add(firemanEditor)
        }
        notifyDataSetChanged()
    }

    private fun getFilteredFiremans(carId: Int): List<Fireman> {
        return allFiremansList.filter { it.selectStatus == null || it.selectStatus == carId }
    }

    companion object {
        private const val UNSELECTED = 0
    }
}
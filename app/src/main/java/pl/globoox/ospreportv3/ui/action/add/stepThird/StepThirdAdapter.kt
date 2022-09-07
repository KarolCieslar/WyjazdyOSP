package pl.globoox.ospreportv3.ui.action.add.stepThird

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemAddActionCarBinding
import pl.globoox.ospreportv3.eventbus.UpdateFiremanFunction
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.views.MarginItemDecoration


class StepThirdAdapter(
    val onFiremanItemClick: ((fireman: Fireman) -> Unit),
    val recyclerView: RecyclerView
) : RecyclerView.Adapter<StepThirdAdapter.ViewHolder>() {

    private var currentExpandedCar = UNSELECTED
    private var itemList: List<Car> = emptyList()
    private var allFiremansList: List<Fireman> = emptyList()
    private lateinit var context: Context
    private lateinit var adapter: FiremanRecyclerAdapter

    init {
        EventBus.getDefault().register(this@StepThirdAdapter)
    }

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

        init {
            binding.expandableLayout.setInterpolator(OvershootInterpolator())
            binding.cardView.setOnClickListener(this)
            prepareAdapterAndSetData(binding, adapterPosition)
            binding.firemanRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        fun bind() {
            val position = adapterPosition
            val isSelected = position == currentExpandedCar
            binding.name.text = itemList[position].name
            binding.cardView.isSelected = isSelected
            binding.expandableLayout.setExpanded(isSelected, false)
            prepareAdapterAndSetData(binding, position)
        }

        override fun onClick(view: View) {
            val holder = recyclerView.findViewHolderForAdapterPosition(currentExpandedCar) as ViewHolder?
            if (holder != null) {
                holder.binding.arrow.rotation = 0f
                holder.binding.cardView.isSelected = false
                holder.binding.expandableLayout.collapse()
            }
            val position = adapterPosition
            if (position == currentExpandedCar) {
                currentExpandedCar = UNSELECTED
                binding.arrow.rotation = 0f
            } else {
                binding.cardView.isSelected = true
                binding.expandableLayout.expand()
                binding.arrow.rotation = 180f
                currentExpandedCar = position
            }
            prepareAdapterAndSetData(binding, adapterPosition)
        }
    }

    private fun prepareAdapterAndSetData(binding: ItemAddActionCarBinding, position: Int) {
        adapter = FiremanRecyclerAdapter(
            onItemClick = { fireman -> onFiremanItemClick(fireman) },
            onCheckBoxChange = { fireman, isChecked -> changeFiremanSelectStatus(fireman, position, isChecked) }
        )
        binding.firemanRecyclerView.adapter = adapter
        val filteredItems = getFilteredFiremans(position)
        binding.emptyView.isVisible = filteredItems.isEmpty()
        adapter.setData(filteredItems)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: UpdateFiremanFunction?) {
        // TODO: Zrobić aby tylko jedna osoba mogła mieć COMMANDERA oraz KIEROWCĘ
        val copyList = getFilteredFiremans(currentExpandedCar)
        adapter.setData(copyList)
    }

    private fun changeFiremanSelectStatus(fireman: Fireman, position: Int, isSelected: Boolean) {
        allFiremansList.first { it == fireman }.selectStatus = if (isSelected) position else null
        adapter.setData(getFilteredFiremans(position))
    }

    fun setCars(list: List<Car>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun setFiremans(list: List<Fireman>) {
        this.allFiremansList = list.toMutableList()
    }

    private fun getFilteredFiremans(position: Int): List<Fireman> {
        return allFiremansList.filter { it.selectStatus == null || it.selectStatus == position }
    }

    companion object {
        private const val UNSELECTED = -1
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        EventBus.getDefault().unregister(this@StepThirdAdapter)
    }
}
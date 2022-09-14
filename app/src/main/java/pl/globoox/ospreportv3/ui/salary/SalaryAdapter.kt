package pl.globoox.ospreportv3.ui.salary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemSalaryFiremanBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.utils.calculateActionHours

class SalaryAdapter: RecyclerView.Adapter<SalaryAdapter.MyViewHolder>() {

    private var firemanList: List<Fireman> = emptyList()
    private var actionList: List<Action> = emptyList()
    private var salaryPerHour: Int = 0
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemSalaryFiremanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSalaryFiremanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(firemanList[position]) {
                val firemanActions = getFiremanActions(this)
                val actionCount = firemanActions.size
                val hoursCount = calculateHours(firemanActions)
                val salary = calculateSalary(hoursCount)

                binding.position.text = "#${position + 1}"
                binding.name.text = this.name
                binding.actionCount.text = actionCount.toString()
                binding.hoursCount.text = hoursCount.toString()
                binding.salary.text = salary.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return firemanList.size
    }

    fun setData(firemanList: List<Fireman>, actions: List<Action>) {
        this.firemanList = firemanList
        this.actionList = actions
        notifyDataSetChanged()
    }

    fun setSalaryValue(value: Int) {
        this.salaryPerHour = value
        notifyDataSetChanged()
    }

    private fun getFiremanActions(fireman: Fireman): List<Action> {
        val firemanActions = mutableListOf<Action>()
        actionList.forEach { action ->
            action.carsInAction.forEach { carInAction ->
                carInAction.firemans.forEach {
                    if (it.id == fireman.id) {
                        firemanActions.add(action)
                    }
                }
            }
        }
        return firemanActions
    }

    private fun calculateSalary(hours: Long): Long {
        return hours * salaryPerHour
    }

    private fun calculateHours(actionList: List<Action>): Long {
        var hours = 0L
        actionList.forEach {
            hours += calculateActionHours(it.outTime, it.inTime)
        }
        return hours
    }
}
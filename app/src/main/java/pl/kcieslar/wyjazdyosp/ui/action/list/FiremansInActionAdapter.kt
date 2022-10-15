package pl.kcieslar.wyjazdyosp.ui.action.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.kcieslar.wyjazdyosp.databinding.ItemFiremanInActionBinding
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction

class FiremansInActionAdapter(
    private val carId: Int
): RecyclerView.Adapter<FiremansInActionAdapter.MyViewHolder>() {

    private var firemanList: List<Fireman> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemFiremanInActionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFiremanInActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(firemanList[position]) {
                val firemanFunctions = this.functions[carId]
                binding.name.text = this.name
                binding.commanderIcon.isVisible = firemanFunctions?.contains(FiremanFunction.COMMANDER) ?: false
                binding.driverIcon.isVisible = firemanFunctions?.contains(FiremanFunction.DRIVER) ?: false
                binding.ownCarIcon.isVisible = firemanFunctions?.contains(FiremanFunction.OWNCAR) ?: false
            }
        }
    }

    override fun getItemCount(): Int {
        return firemanList.size
    }

    fun setList(firemanList: List<Fireman>) {
        this.firemanList = firemanList
        notifyDataSetChanged()
    }
}
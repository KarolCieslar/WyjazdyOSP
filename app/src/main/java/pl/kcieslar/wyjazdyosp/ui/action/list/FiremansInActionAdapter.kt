package pl.kcieslar.wyjazdyosp.ui.action.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.kcieslar.wyjazdyosp.databinding.ItemFiremanInActionBinding
import pl.kcieslar.wyjazdyosp.model.Fireman

class FiremansInActionAdapter: RecyclerView.Adapter<FiremansInActionAdapter.MyViewHolder>() {

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
                binding.name.text = this.name
                binding.commanderIcon.isVisible = this.commanderFunction
                binding.driverIcon.isVisible = this.driverFunction
                binding.ownCarIcon.isVisible = this.ownCarFunction
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
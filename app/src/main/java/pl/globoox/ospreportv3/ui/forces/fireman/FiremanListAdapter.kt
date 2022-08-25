package pl.globoox.ospreportv3.ui.forces.fireman

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesFiremanListBinding
import pl.globoox.ospreportv3.model.Fireman

class FiremanListAdapter : RecyclerView.Adapter<FiremanListAdapter.MyViewHolder>() {

    private var firemanList: List<Fireman> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesFiremanListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesFiremanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(firemanList[position]) {
                if (position % 2 != 0) {
                    binding.item.setCardBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
                }
                binding.position.text = (position + 1).toString()
                binding.name.text = this.name
                binding.item.setOnClickListener {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return firemanList.size
    }

    fun setData(firemanList: List<Fireman>) {
        this.firemanList = firemanList
        notifyDataSetChanged()
    }
}
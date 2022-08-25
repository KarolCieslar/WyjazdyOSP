package pl.globoox.ospreportv3.ui.forces.fireman

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.databinding.ItemForcesFiremanListBinding
import pl.globoox.ospreportv3.model.Fireman

class FiremanListAdapter : RecyclerView.Adapter<FiremanListAdapter.MyViewHolder>() {

    private var firemanList: List<Fireman> = emptyList()

    inner class MyViewHolder(val binding: ItemForcesFiremanListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesFiremanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(firemanList[position]){
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
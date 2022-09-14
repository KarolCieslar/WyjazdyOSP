package pl.globoox.ospreportv3.ui.forces.fireman

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesFiremanListBinding
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.forces.fireman.FiremanListAdapter

class FiremanListAdapter(
    val onRemoveClick: ((fireman: Fireman) -> Unit),
    val onEditClick: ((fireman: Fireman) -> Unit)
) : RecyclerView.Adapter<FiremanListAdapter.MyViewHolder>() {

    private var firemanList: List<Fireman> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesFiremanListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesFiremanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(firemanList[position]){
                binding.position.text = "${position + 1}."
                binding.name.text = this.name

                binding.optionsIcon.setOnClickListener {
                    val popupMenu = PopupMenu(context, binding.optionsIcon)
                    popupMenu.gravity = Gravity.END;
                    popupMenu.menuInflater.inflate(R.menu.popup_forces_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove ->
                                onRemoveClick(this)
                            R.id.edit ->
                                onEditClick(this)
                        }
                        true
                    }
                    popupMenu.show()
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
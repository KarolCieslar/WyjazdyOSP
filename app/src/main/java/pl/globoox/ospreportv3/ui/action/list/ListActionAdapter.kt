package pl.globoox.ospreportv3.ui.action.list

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ItemForcesCarListBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car

class ListActionAdapter(
    val onItemClick: ((car: Car) -> Unit),
    val onRemoveClick: ((car: Car) -> Unit),
    val onEditClick: ((car: Car) -> Unit)
) : RecyclerView.Adapter<ListActionAdapter.MyViewHolder>() {

    private var actionList: List<Action> = emptyList()
    private lateinit var context: Context

    inner class MyViewHolder(val binding: ItemForcesCarListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemForcesCarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(actionList[position]) {
                Log.d("dasdas", "${this}")
//                binding.name.text = this.name
//                binding.item.setOnClickListener {
//                    onItemClick(this)
//                }
//
//                binding.optionsIcon.setOnClickListener {
//                    val popupMenu = PopupMenu(context, binding.optionsIcon)
//                    popupMenu.gravity = Gravity.END
//                    popupMenu.menuInflater.inflate(R.menu.popup_forces_menu, popupMenu.menu)
//                    popupMenu.setOnMenuItemClickListener { item ->
//                        when (item.itemId) {
//                            R.id.remove ->
//                                onRemoveClick(this)
//                            R.id.edit ->
//                                onEditClick(this)
//                        }
//                        true
//                    }
//                    popupMenu.show()
//                }

            }
        }
    }

    override fun getItemCount(): Int {
        return actionList.size
    }

    fun setData(actionList: List<Action>) {
        this.actionList = actionList
        notifyDataSetChanged()
    }
}
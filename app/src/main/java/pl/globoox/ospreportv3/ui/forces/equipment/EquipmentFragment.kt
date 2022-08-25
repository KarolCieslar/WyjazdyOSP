package pl.globoox.ospreportv3.ui.forces.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentForcesEquipmentListBinding
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel
import pl.globoox.ospreportv3.views.AddForcesDialogView
import pl.globoox.ospreportv3.views.MarginItemDecoration

class EquipmentFragment : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesEquipmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesEquipmentListBinding.inflate(inflater, container, false)

        val adapter = EquipmentListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
        )

        viewModel.equipmentList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isEmpty()
            binding.floatingActionButton.isVisible = it.isNotEmpty()
            if (it.isEmpty()) binding.emptyView.apply {
                setMainText(resources.getString(R.string.equipment_fragment_empty_view_main))
                setDescription(resources.getString(R.string.equipment_fragment_empty_view_description))
                setButtonData(resources.getString(R.string.equipment_fragment_empty_view_button)) { openAddDialog() }
            }
            adapter.setData(it)
        })

        binding.floatingActionButton.setOnClickListener { openAddDialog() }
        return binding.root
    }
    
    private fun openAddDialog() {
        val dialog = AddForcesDialogView(requireContext())
        dialog.setTitle(resources.getString(R.string.equipment_fragment_add_dialog_title))
        dialog.setDescription(resources.getString(R.string.equipment_fragment_add_dialog_description))
        dialog.setOnPrimaryButtonClickListener { editTextString -> viewModel.addEquipment(Equipment(0, editTextString)) }
    }
}
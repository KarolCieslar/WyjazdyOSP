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
import com.google.android.material.snackbar.Snackbar
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentForcesEquipmentListBinding
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel
import pl.globoox.ospreportv3.views.AddForcesDialogView
import pl.globoox.ospreportv3.views.ConfirmDialogView
import pl.globoox.ospreportv3.views.EditForcesDialogView
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

        val adapter = EquipmentListAdapter(
            onItemClick = { },
            onRemoveClick = {equipment -> removeEquipment(equipment) },
            onEditClick = {equipment -> openEditDialog(equipment) })
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

    private fun removeEquipment(equipment: Equipment) {
        val dialog = ConfirmDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
            setDescription(resources.getString(R.string.equipment_fragment_remove_dialog_description, equipment.name))
            setOnPrimaryButtonClickListener {
                showSnackBar(resources.getString(R.string.removed_successfully))
                viewModel.removeEquipment(equipment)
            }
        }
    }

    private fun openAddDialog() {
        val dialog = AddForcesDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.equipment_fragment_add_dialog_title))
            setDescription(resources.getString(R.string.equipment_fragment_add_dialog_description))
            setOnPrimaryButtonClickListener { editTextString -> viewModel.addEquipment(Equipment(0, editTextString)) }
        }
    }

    private fun openEditDialog(equipment: Equipment) {
        val dialog = EditForcesDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.equipment_fragment_edit_dialog_title))
            setDescription(resources.getString(R.string.equipment_fragment_edit_dialog_description))
            setContent(equipment.name.toString())
            setOnPrimaryButtonClickListener { editTextString -> viewModel.editEquipment(Equipment(equipment.id, editTextString)) }
        }
    }
}
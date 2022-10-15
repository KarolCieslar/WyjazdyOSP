package pl.kcieslar.wyjazdyosp.ui.forces.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kcieslar.wyjazdyosp.R
import kcieslar.wyjazdyosp.databinding.FragmentForcesViewPagerBinding
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.utils.ForcesStringType
import pl.kcieslar.wyjazdyosp.utils.getForcesString
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.viewmodel.ForcesViewModel
import pl.kcieslar.wyjazdyosp.views.AddOrEditForcesDialogView
import pl.kcieslar.wyjazdyosp.views.ConfirmDialogView

class ForcesViewPagerFragment(
    private val forcesDataType: ForcesDataType,
    private val openAddDialogAtInit: Boolean? = null
) : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataList: LiveData<List<Forces>>
    private lateinit var adapter: ViewPagerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesViewPagerBinding.inflate(inflater, container, false)
        this.dataList = when (forcesDataType) {
            ForcesDataType.CAR -> viewModel.carList as LiveData<List<Forces>>
            ForcesDataType.FIREMAN -> viewModel.firemanList as LiveData<List<Forces>>
            ForcesDataType.EQUIPMENT -> viewModel.equipmentList as LiveData<List<Forces>>
        }

        adapter = ViewPagerListAdapter(
            onItemClick = { item -> openEditDialog(item) },
            onEditClick = { item -> removeItem(item) })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        dataList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isEmpty()
            binding.floatingActionButton.isVisible = it.isNotEmpty()
            if (it.isEmpty()) binding.emptyView.apply {
                setMainText(getForcesString(context, ForcesStringType.EMPTY_VIEW_MAIN, forcesDataType))
                setDescription(getForcesString(context, ForcesStringType.EMPTY_VIEW_DESCRIPTION, forcesDataType))
                setButtonData(getForcesString(context, ForcesStringType.EMPTY_VIEW_BUTTON, forcesDataType)) { openAddDialog() }
            }
            adapter.setItems(it)
        })

        binding.floatingActionButton.setOnClickListener { openAddDialog() }

        if (openAddDialogAtInit == true) {
            openAddDialog()
        }

        return binding.root
    }

    private fun removeItem(item: Forces) {
        val dialog = ConfirmDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
            setDescription(getForcesString(context, ForcesStringType.REMOVE_FIRCES_DIALOG_DESCRIPTION, forcesDataType, item.name))
            setOnPrimaryButtonClickListener {
                showSnackBar(resources.getString(R.string.removed_successfully))
                viewModel.removeItem(item)
            }
        }
    }

    private fun openAddDialog() {
        val dialog = AddOrEditForcesDialogView(requireContext())
        dialog.apply {
            setTitle(getForcesString(context, ForcesStringType.ADD_DIALOG_TITLE, forcesDataType))
            setPrimaryButtonText(resources.getString(R.string.button_add))
            setOnPrimaryButtonClickListener { editTextString ->
                if (!isObjectExist(editTextString)) {
                    viewModel.addItem(forcesDataType, editTextString)
                    dismiss()
                } else {
                    setError(getForcesString(context, ForcesStringType.OBJECT_ALREADY_EXIST, forcesDataType))
                }
            }
        }
    }

    private fun openEditDialog(item: Forces) {
        val dialog = AddOrEditForcesDialogView(requireContext())
        dialog.apply {
            setTitle(getForcesString(context, ForcesStringType.EDIT_DIALOG_TITLE, forcesDataType))
            setPrimaryButtonText(resources.getString(R.string.button_save))
            setEditTextValue(item.name)
            setOnPrimaryButtonClickListener { editTextString ->
                if (!isObjectExist(editTextString, item.name)) {
                    viewModel.editItem(forcesDataType, item.id, editTextString)
                    dismiss()
                } else {
                    setError(getForcesString(context, ForcesStringType.OBJECT_ALREADY_EXIST, forcesDataType))
                }
            }
        }
    }

    private fun isObjectExist(newItemName: String, currentEditedItemName: String? = null): Boolean {
        return if (currentEditedItemName == newItemName) {
            false
        } else {
            adapter.getItems().firstOrNull { it.name.lowercase().trim() == newItemName.lowercase().trim() } != null
        }
    }
}
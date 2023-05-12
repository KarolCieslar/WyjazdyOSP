package pl.kcieslar.wyjazdyosp.ui.forces.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseForcesResponse
import pl.kcieslar.wyjazdyosp.databinding.FragmentForcesViewPagerBinding
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.FORCES_TYPE_ARG
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesViewModel
import pl.kcieslar.wyjazdyosp.utils.ForcesStringType
import pl.kcieslar.wyjazdyosp.utils.getForcesString
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.views.AddOrEditForcesDialogView
import pl.kcieslar.wyjazdyosp.views.ConfirmDialogView

@AndroidEntryPoint
class ForcesViewPagerFragment : Fragment() {
    private var forcesDataType: ForcesDataType = ForcesDataType.CAR
    private var openAddDialogAtInit: Boolean? = null

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataList: LiveData<FirebaseForcesResponse>
    private lateinit var adapter: ViewPagerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey(FORCES_TYPE_ARG)
        }?.apply {
            forcesDataType = this.getSerializable(FORCES_TYPE_ARG) as ForcesDataType
        }
        this.dataList = when (forcesDataType) {
            ForcesDataType.CAR -> viewModel.carList as LiveData<FirebaseForcesResponse>
            ForcesDataType.FIREMAN -> viewModel.firemanList as LiveData<FirebaseForcesResponse>
            ForcesDataType.EQUIPMENT -> viewModel.equipmentList as LiveData<FirebaseForcesResponse>
        }

        adapter = ViewPagerListAdapter(
            onItemClick = { item -> openEditDialog(item) },
            onEditClick = { item -> removeItem(item) })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.viewModelEvents.observe(viewLifecycleOwner) {
            when (it) {
                is ForcesViewModel.LoadingData -> {
                    showLoader(true)
                    showErrorView(false)
                }
                is ForcesViewModel.CallBackSuccessfully -> {
                    showLoader(false)
                    showErrorView(false)
                    viewModel.refreshData(forcesDataType)
                }
                is ForcesViewModel.CallBackError -> {
                    showLoader(false)
                    showErrorView(true, it.exception?.message.toString())
                }
            }
        }

        dataList.observe(viewLifecycleOwner, Observer {
            if (it.exception != null) {
                Log.e("ForcesViewPagerFragment", it.exception!!.message.toString())
            } else {
                it.list?.let { list ->
                    showLoader(false)
                    binding.errorView.isVisible = list.isEmpty()
                    binding.floatingActionButton.isVisible = list.isNotEmpty()
                    if (list.isEmpty()) binding.errorView.apply {
                        setMainText(getForcesString(context, ForcesStringType.EMPTY_VIEW_MAIN, forcesDataType))
                        setDescription(getForcesString(context, ForcesStringType.EMPTY_VIEW_DESCRIPTION, forcesDataType))
                        setButtonData(getForcesString(context, ForcesStringType.EMPTY_VIEW_BUTTON, forcesDataType)) { openAddDialog() }
                    }
                    adapter.setItems(list)
                }
            }
        })

        binding.floatingActionButton.setOnClickListener { openAddDialog() }

        if (openAddDialogAtInit == true) {
            openAddDialog()
        }
    }

    private fun showLoader(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun showErrorView(show: Boolean, errorMessage: String? = null) {
        binding.errorView.apply {
            isVisible = show
            setMainText("WYSTĄPIŁ BŁĄD")
            errorMessage?.let {
                setDescription(it)
            }
            setButtonData("SPRÓBUJ PONOWNIE") { viewModel.refreshData(forcesDataType) }
        }
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
            setOnPrimaryButtonClickListener { objectName ->
                if (!isObjectExist(objectName)) {
                    viewModel.addItem(forcesDataType, objectName)
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
                    viewModel.editItem(item, editTextString)
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
package pl.kcieslar.wyjazdyosp.ui.forces.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentForcesViewPagerBinding
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.FORCES_TYPE_ARG
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesViewModel
import pl.kcieslar.wyjazdyosp.utils.ForcesStringType
import pl.kcieslar.wyjazdyosp.utils.getForcesString
import pl.kcieslar.wyjazdyosp.utils.logFirebaseCrash
import pl.kcieslar.wyjazdyosp.views.AddOrEditForcesDialogView
import pl.kcieslar.wyjazdyosp.views.ConfirmDialogView
import pl.kcieslar.wyjazdyosp.views.RetryDialogView

@AndroidEntryPoint
class ForcesViewPagerFragment : Fragment() {
    private var forcesDataType: ForcesDataType = ForcesDataType.CAR

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ViewPagerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            if (it.containsKey(FORCES_TYPE_ARG)) forcesDataType = it.getSerializable(FORCES_TYPE_ARG) as ForcesDataType
        }
        showShimmer(true)

        adapter = ViewPagerListAdapter(
            onItemClick = { item -> openEditDialog(item) },
            onEditClick = { item -> removeItem(item) })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.viewModelEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ForcesViewModel.LoadingData -> {
                    showShimmer(true)
                    showCallErrorView(false)
                }

                is ForcesViewModel.CrudItemError -> {
                    showErrorDialogWithRetry { event.retryAction() }
                    logFirebaseCrash(event.exception!!, "ForcesViewPagerFragment CrudItemError")
                }
            }
        }

        viewModel.forces.observe(viewLifecycleOwner, Observer {
            if (it.exception != null) {
                logFirebaseCrash(it.exception!!, "ForcesViewPagerFragment - viewModel.forces.observe exception != null")
                showShimmer(false)
                showCallErrorView(true, it.exception?.message.toString())
            } else {
                it.getSpecificTypeList(forcesDataType).let { list ->
                    binding.errorView.isVisible = list.isEmpty()
                    binding.recyclerView.isVisible = list.isNotEmpty()
                    binding.floatingActionButton.isVisible = list.isNotEmpty()
                    if (list.isEmpty()) binding.errorView.apply {
                        setMainText(getForcesString(context, ForcesStringType.EMPTY_VIEW_MAIN, forcesDataType))
                        setDescription(getForcesString(context, ForcesStringType.EMPTY_VIEW_DESCRIPTION, forcesDataType))
                        setButtonData(getForcesString(context, ForcesStringType.EMPTY_VIEW_BUTTON, forcesDataType)) { openAddDialog() }
                    }
                    showShimmer(false)
                    adapter.setItems(list)
                }
            }
        })
        binding.floatingActionButton.setOnClickListener { openAddDialog() }
    }

    private fun showShimmer(show: Boolean) {
        if (show) binding.floatingActionButton.isVisible = false
        binding.shimmerFrameLayout.apply {
            if (show) startShimmerAnimation() else stopShimmerAnimation()
            isVisible = show
        }
    }

    private fun showCallErrorView(show: Boolean, errorMessage: String? = null) {
        binding.recyclerView.isVisible = !show
        if (show) binding.floatingActionButton.isVisible = false
        binding.errorView.apply {
            isVisible = show
            setMainText(context.getString(R.string.error_occured))
            errorMessage?.let {
                setDescription(it)
            }
            setButtonData("SPRÓBUJ PONOWNIE") {
                viewModel.refreshData()
            }
        }
    }

    private fun showErrorDialogWithRetry(retryAction: () -> Unit) {
        val retryDialog = RetryDialogView(requireContext())
        retryDialog.setRetryButtonAction(retryAction)
    }

    private fun removeItem(item: Forces) {
        val confirmDialog = ConfirmDialogView(requireContext())
        confirmDialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
            setDescription(getForcesString(context, ForcesStringType.REMOVE_FIRCES_DIALOG_DESCRIPTION, forcesDataType, item.name))
            setOnPrimaryButtonClickListener {
                viewModel.removeItem(item)
                dismiss()
            }
        }
    }

    private fun openAddDialog() {
        val addOrEditDialog = AddOrEditForcesDialogView(requireContext())
        addOrEditDialog.apply {
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
        val addOrEditDialog = AddOrEditForcesDialogView(requireContext())
        addOrEditDialog.apply {
            setTitle(getForcesString(context, ForcesStringType.EDIT_DIALOG_TITLE, forcesDataType))
            setPrimaryButtonText(resources.getString(R.string.button_save))
            setEditTextValue(item.name)
            setOnPrimaryButtonClickListener { editTextString ->
                if (!isObjectExist(editTextString, item.name)) {
                    item.name = editTextString
                    viewModel.editItem(item)
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
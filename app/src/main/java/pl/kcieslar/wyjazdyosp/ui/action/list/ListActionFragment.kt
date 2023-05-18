package pl.kcieslar.wyjazdyosp.ui.action.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentListActionBinding
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.views.ConfirmDialogView
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes
import pl.kcieslar.wyjazdyosp.views.RetryDialogView

@AndroidEntryPoint
class ListActionFragment : Fragment() {

    private val viewModel: ActionListViewModel by viewModels()
    private var _binding: FragmentListActionBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListActionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showShimmer(true)

        adapter = ListActionAdapter(
            onEditButtonClick = { action -> openEditFragment(action) },
            onRemoveButtonClick = { action -> showRemoveDialog(action) }
        )
        val recyclerView = binding.actionListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.viewModelEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ActionListViewModel.LoadingData -> {
                    showShimmer(true)
                    showCallErrorView(false)
                }

                is ActionListViewModel.RemovedActionSuccessfully -> {
                    adapter.removeFromExpanded(event.action)
                }

                is ActionListViewModel.RemovedActionError -> {
                    showErrorDialogWithRetry { viewModel.removeAction(event.action) }
                    Log.e("ListActionFragment CallBackError", event.exception.toString())
                }
            }
        }

        // TODO: Poprawić wszystkie showErrorView bo raz jest funkcja a raz bezposrednie wołanie na widoku
        // TODO: Zrobić export starej bazy ROOM DATABASE
        // TODO: W KAŻDYM EXCEPTIONIE DAWAĆ SENDA DO FIREBASE CRASH
        viewModel.actions.observe(viewLifecycleOwner) {
            if (it.exception != null) {
                Log.e("ListActionFragment", it.exception!!.message.toString())
                showCallErrorView(true, it.exception?.message.toString())
                showShimmer(false)
            } else {
                it.list?.let { list ->
                    showShimmer(false)
                    binding.errorView.isVisible = list.isEmpty()
                    binding.actionListRecyclerView.isVisible = list.isNotEmpty()
                    if (list.isEmpty()) binding.errorView.apply {
                        setMainText(resources.getString(R.string.list_action_fragment_empty_view_main))
                        setDescription(resources.getString(R.string.list_action_fragment_empty_view_description))
                        showButton(false)
                    }
                    adapter.setList(list)
                }
            }
        }

        viewModel.isAnyCarsAndFiremans.observe(viewLifecycleOwner) { isAnyCarsAndFiremans ->
            binding.floatingActionButton.apply {
                alpha = if (isAnyCarsAndFiremans) 1f else 0.5f
                setOnClickListener {
                    if (isAnyCarsAndFiremans) {
                        findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(null))
                    } else {
                        showSnackBar(resources.getString(R.string.list_action_no_cars_or_firemans))
                    }
                }
            }
        }

        setHelpDialogString(HelpDialogStringRes.ACTION_LIST)
    }

    private fun showShimmer(show: Boolean) {
        binding.shimmerFrameLayout.apply {
            if (show) startShimmerAnimation() else stopShimmerAnimation()
            isVisible = show
        }
        binding.floatingActionButton.isVisible = !show
    }

    private fun showCallErrorView(show: Boolean, errorMessage: String? = null) {
        binding.actionListRecyclerView.isVisible = !show
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

    private fun openEditFragment(action: Action) {
        findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(action))
    }

    private fun showRemoveDialog(action: Action) {
        val confirmDialog = ConfirmDialogView(requireContext())
        confirmDialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
            setDescription(context.resources.getString(R.string.list_action_fragment_remove_dialog_description, action.number))
            setOnPrimaryButtonClickListener {
                confirmDialog.dismiss()
                viewModel.removeAction(action)
            }
        }
    }

    private fun showErrorDialogWithRetry(retryAction: () -> Unit) {
        val retryDialog = RetryDialogView(requireContext())
        retryDialog.setRetryButtonAction(retryAction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
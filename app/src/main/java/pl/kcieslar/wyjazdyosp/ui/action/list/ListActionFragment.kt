package pl.kcieslar.wyjazdyosp.ui.action.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        adapter = ListActionAdapter(
            onEditButtonClick = { action -> openEditFragment(action) },
            onRemoveButtonClick = { action -> removeItem(action) }
        )
        val recyclerView = binding.actionListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        viewModel.actionList.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                binding.errorView.isVisible = it.isEmpty()
//                if (it.isEmpty()) binding.errorView.apply {
//                    setMainText(resources.getString(R.string.list_action_fragment_empty_view_main))
//                    setDescription(resources.getString(R.string.list_action_fragment_empty_view_description))
//                }
//                adapter.setList(it)
//            }
//        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(null))
        }

        setHelpDialogString(HelpDialogStringRes.ACTION_LIST)
        return binding.root
    }

    private fun openEditFragment(action: Action) {
        findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(action))
    }

    private fun removeItem(action: Action) {
        val dialog = ConfirmDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
            setDescription(context.resources.getString(R.string.list_action_fragment_remove_dialog_description, action.number))
            setOnPrimaryButtonClickListener {
                showSnackBar(resources.getString(R.string.removed_successfully))
                adapter.removeFromExpanded(action)
                viewModel.removeAction(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
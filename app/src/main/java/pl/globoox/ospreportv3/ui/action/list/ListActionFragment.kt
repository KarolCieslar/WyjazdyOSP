package pl.globoox.ospreportv3.ui.action.list

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
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentListActionBinding
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.viewmodel.ActionListViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration

class ListActionFragment : Fragment() {

    private val viewModel: ActionListViewModel by viewModels()
    private var _binding: FragmentListActionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListActionBinding.inflate(inflater, container, false)

        val adapter = ListActionAdapter(onEditButtonClick = { action -> openEditFragment(action) })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin16))
        )

        viewModel.actionList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isEmpty()
            if (it.isEmpty()) binding.emptyView.apply {
                setMainText(resources.getString(R.string.list_action_fragment_empty_view_main))
                setDescription(resources.getString(R.string.list_action_fragment_empty_view_description))
            }
            adapter.setList(it)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(null))
        }

        return binding.root
    }

    private fun openEditFragment(action: Action) {
        findNavController().navigate(ListActionFragmentDirections.actionListActionToAddOrEditAction(action))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
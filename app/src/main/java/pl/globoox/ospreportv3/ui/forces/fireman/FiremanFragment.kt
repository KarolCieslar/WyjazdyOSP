package pl.globoox.ospreportv3.ui.forces.fireman

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
import pl.globoox.ospreportv3.databinding.FragmentForcesFiremanListBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel
import pl.globoox.ospreportv3.views.AddForcesDialogView
import pl.globoox.ospreportv3.views.MarginItemDecoration

class FiremanFragment : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesFiremanListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesFiremanListBinding.inflate(inflater, container, false)

        val adapter = FiremanListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
        )

        viewModel.firemanList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isEmpty()
            binding.floatingActionButton.isVisible = it.isNotEmpty()
            if (it.isEmpty()) binding.emptyView.apply {
                setMainText(resources.getString(R.string.fireman_fragment_empty_view_main))
                setDescription(resources.getString(R.string.fireman_fragment_empty_view_description))
                setButtonData(resources.getString(R.string.fireman_fragment_empty_view_button)) { openAddDialog() }
            }
            adapter.setData(it)
        })

        binding.floatingActionButton.setOnClickListener { openAddDialog() }
        return binding.root
    }

    private fun openAddDialog() {
        val dialog = AddForcesDialogView(requireContext())
        dialog.setTitle(resources.getString(R.string.fireman_fragment_add_dialog_title))
        dialog.setDescription(resources.getString(R.string.fireman_fragment_add_dialog_description))
        dialog.setOnPrimaryButtonClickListener { editTextString -> viewModel.addFireman(Fireman(0, editTextString)) }
    }
}
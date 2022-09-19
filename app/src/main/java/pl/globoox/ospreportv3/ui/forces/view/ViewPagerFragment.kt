package pl.globoox.ospreportv3.ui.forces.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentForcesViewPagerBinding
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.ui.forces.ForcesViewPagerAdapter
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel
import pl.globoox.ospreportv3.views.AddForcesDialogView
import pl.globoox.ospreportv3.views.ConfirmDialogView
import pl.globoox.ospreportv3.views.EditForcesDialogView
import pl.globoox.ospreportv3.views.MarginItemDecoration

class ViewPagerFragment(
    private val forcesDataType: ForcesViewPagerAdapter.ForcesDataType
) : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataList: LiveData<List<Any>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesViewPagerBinding.inflate(inflater, container, false)
        this.dataList = when (forcesDataType) {
            ForcesViewPagerAdapter.ForcesDataType.CAR -> viewModel.carList as LiveData<List<Any>>
            ForcesViewPagerAdapter.ForcesDataType.FIREMAN -> viewModel.firemanList as LiveData<List<Any>>
            ForcesViewPagerAdapter.ForcesDataType.EQUIPMENT -> viewModel.equipmentList as LiveData<List<Any>>
        }

        val adapter = ViewPagerListAdapter(
            onItemClick = { item -> openEditDialog(item) },
            onEditClick = { item -> removeItem(item) })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
        )

        dataList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isEmpty()
            binding.floatingActionButton.isVisible = it.isNotEmpty()
            if (it.isEmpty()) binding.emptyView.apply {
                setMainText(resources.getString(R.string.car_fragment_empty_view_main))
                setDescription(resources.getString(R.string.car_fragment_empty_view_description))
                setButtonData(resources.getString(R.string.car_fragment_empty_view_button)) { openAddDialog() }
            }
            adapter.setData(it)
        })

        binding.floatingActionButton.setOnClickListener { openAddDialog() }

        return binding.root
    }

    private fun removeItem(item: Any) {
        val dialog = ConfirmDialogView(requireContext())
        dialog.apply {
            setTitle(resources.getString(R.string.confirm_dialog_title))
//            setDescription(resources.getString(R.string.car_fragment_remove_dialog_description, car.name))
            setOnPrimaryButtonClickListener {
                showSnackBar(resources.getString(R.string.removed_successfully))
                viewModel.removeItem(item)
            }
        }
    }

    private fun openAddDialog() {
//        val dialog = AddForcesDialogView(requireContext())
//        dialog.apply {
//            setTitle(resources.getString(R.string.car_fragment_add_dialog_title))
//            setDescription(resources.getString(R.string.car_fragment_add_dialog_description))
//            setOnPrimaryButtonClickListener { editTextString -> viewModel.addItem(Car(0, editTextString)) }
//        }
    }

    private fun openEditDialog(item: Any) {
//        val dialog = EditForcesDialogView(requireContext())
//        dialog.apply {
//            setTitle(resources.getString(R.string.car_fragment_edit_dialog_title))
//            setDescription(resources.getString(R.string.car_fragment_edit_dialog_description))
//            setContent(car.name.toString())
//            setOnPrimaryButtonClickListener { editTextString -> viewModel.editItem(Car(item.id, editTextString)) }
//        }
    }
}
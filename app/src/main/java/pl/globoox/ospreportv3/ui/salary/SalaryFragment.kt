package pl.globoox.ospreportv3.ui.salary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuinden.livedatacombinetuplekt.combineTuple
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentSalaryBinding
import pl.globoox.ospreportv3.ui.action.list.ListActionFragmentDirections
import pl.globoox.ospreportv3.viewmodel.SalaryViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration
import pl.globoox.ospreportv3.views.SalaryValueDialogView

class SalaryFragment : Fragment() {

    private val viewModel: SalaryViewModel by viewModels()
    private var _binding: FragmentSalaryBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreferences? = null
    private var salaryPerHour = -1
    private val adapter = SalaryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalaryBinding.inflate(inflater, container, false)
        sharedPref = activity?.getSharedPreferences("SALARY_HOUR_VALUE", Context.MODE_PRIVATE)
        getNewSalaryValue()
        buildFragmentUI()

        return binding.root
    }

    private fun buildFragmentUI() {
        binding.salaryValueEdit.setOnClickListener {
            openSalaryValueEditDialog(salaryPerHour)
        }

        if (salaryPerHour == -1) {
            binding.viewGroup.isVisible = false
            binding.emptyView.apply {
                isVisible = true
                setMainText(resources.getString(R.string.salary_fragment_salary_not_set))
                setDescription(resources.getString(R.string.salary_fragment_salary_not_set_description))
                setButtonData("Ustal Stawkę") { openSalaryValueEditDialog(0) }
            }
        } else {
            binding.viewGroup.isVisible = false
            val recyclerView = binding.recyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
            )
            adapter.setSalaryValue(salaryPerHour)

            combineTuple(viewModel.firemanList, viewModel.firemanActions).observe(viewLifecycleOwner) { (firemans, actions) ->
                if (firemans != null && actions != null) {
                    binding.emptyView.isVisible = firemans.isEmpty()
                    binding.viewGroup.isVisible = firemans.isNotEmpty()
                    if (firemans.isEmpty()) binding.emptyView.apply {
                        setMainText(resources.getString(R.string.fireman_fragment_empty_view_main))
                        setDescription(resources.getString(R.string.fireman_fragment_empty_view_description))
                        setButtonData(resources.getString(R.string.fireman_fragment_empty_view_button)) {
                            findNavController().navigate(SalaryFragmentDirections.actionSalaryFragmentToForcesFragment(1))
                        }
                    }
                    adapter.setData(firemans, actions)
                }
            }
        }
    }

    private fun openSalaryValueEditDialog(salaryPerHour: Int) {
        val dialog = SalaryValueDialogView(requireContext(), salaryPerHour)
        dialog.setOnPrimaryButtonClickListener { selectedValue ->
            sharedPref?.edit()?.putInt("SALARY_HOUR_VALUE", selectedValue)?.apply()
            getNewSalaryValue()
            adapter.setSalaryValue(salaryPerHour)
            buildFragmentUI()
        }
    }

    private fun getNewSalaryValue() {
        salaryPerHour = sharedPref?.getInt("SALARY_HOUR_VALUE", -1) ?: -1
    }
}
package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepSecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuinden.livedatacombinetuplekt.combineTuple
import org.greenrobot.eventbus.EventBus
import kcieslar.wyjazdyosp.R
import kcieslar.wyjazdyosp.databinding.FragmentStepSecondBinding
import pl.kcieslar.wyjazdyosp.eventbus.SetCurrentViewPagerItem
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddOrEditActionFragment
import pl.kcieslar.wyjazdyosp.utils.mergeList
import pl.kcieslar.wyjazdyosp.utils.setHorizontalMargin
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.viewmodel.AddActionViewModel


class StepSecondFragment: Fragment() {

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StepSecondAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepSecondBinding.inflate(inflater, container, false)

        prepareAdapter()

        combineTuple(viewModel.equipmentList, viewModel.carList).observe(viewLifecycleOwner) { (equipmentList, carList) ->
            if (equipmentList != null && carList != null) {
                val carsAndEquipments = mergeList(equipmentList, carList)
                val isAnyCar = carList.isNotEmpty()
                binding.viewGroup.isVisible = isAnyCar
                binding.emptyView.isVisible = !isAnyCar
                setBottomButtonsListener(isAnyCar)
                if (!isAnyCar) {
                    binding.emptyView.apply {
                        setMainText(resources.getString(R.string.add_action_empty_view_main))
                        setDescription(resources.getString(R.string.add_action_empty_view_additional_description))
                    }
                }
                adapter.setData(carsAndEquipments.toMutableList())
            }
        }

        return binding.root
    }

    private fun prepareAdapter() {
        adapter = StepSecondAdapter(viewModel.action)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun isFormValid(): Boolean {
        return adapter.getSelectedItems().filterIsInstance<Car>().isNotEmpty()
    }

    private fun setBottomButtonsListener(isAnyCar: Boolean) {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                val selectedCars = adapter.getSelectedItems().filterIsInstance<Car>()
                val selectedEquipment = adapter.getSelectedItems().filterIsInstance<Equipment>()
                viewModel.setSelectedCars(selectedCars)
                viewModel.setSelectedEquipments(selectedEquipment)
                EventBus.getDefault().post(SetCurrentViewPagerItem(AddOrEditActionFragment.StepNumber.THIRD))
            } else {
                showSnackBar(resources.getString(R.string.form_none_cars_selected))
            }
        }

        binding.cancelButton.setClickListener {
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddOrEditActionFragment.StepNumber.FIRST))
        }

        if (!isAnyCar) {
            binding.cancelButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = binding.constraintLayout.id
                endToEnd = binding.constraintLayout.id
                bottomToBottom = binding.constraintLayout.id
            }
            binding.cancelButton.setHorizontalMargin(R.dimen.margin70)
        } else {
            binding.cancelButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = binding.constraintLayout.id
                endToEnd = binding.guidelineCenter.id
                bottomToBottom = binding.constraintLayout.id
            }
            binding.cancelButton.setHorizontalMargin(R.dimen.margin10)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
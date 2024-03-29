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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentStepSecondBinding
import pl.kcieslar.wyjazdyosp.eventbus.SetCurrentViewPagerItem
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddActionViewModel
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddOrEditActionFragment
import pl.kcieslar.wyjazdyosp.utils.logFirebaseCrash
import pl.kcieslar.wyjazdyosp.utils.mergeList
import pl.kcieslar.wyjazdyosp.utils.setHorizontalMargin
import pl.kcieslar.wyjazdyosp.utils.showSnackBar

class StepSecondFragment : Fragment() {

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
        showLoader(true)

        viewModel.forces.observe(viewLifecycleOwner, Observer {
            showLoader(false)
            if (it.exception != null) {
                logFirebaseCrash(it.exception!!, "StepSecondFragment - viewModel.forces.observe exception != null")
                showCallErrorView(true, it.exception?.message.toString())
                setBottomButtonsListener(shouldShowNextButton = false)
            } else {
                val carsAndEquipments = mergeList(it.getEquipmentList(), it.getCarList())
                val isAnyCar = it.getCarList().isNotEmpty()
                binding.recyclerView.isVisible = isAnyCar
                binding.errorView.isVisible = !isAnyCar
                setBottomButtonsListener(shouldShowNextButton = isAnyCar)
                if (!isAnyCar) {
                    binding.errorView.apply {
                        setMainText(resources.getString(R.string.add_action_empty_view_main))
                        setDescription(resources.getString(R.string.add_action_empty_view_additional_description))
                        showButton(false)
                    }
                }
                adapter.setData(carsAndEquipments.toMutableList())
            }
        })

        return binding.root
    }

    private fun showLoader(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun showCallErrorView(show: Boolean, errorMessage: String? = null) {
        binding.recyclerView.isVisible = !show
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

    private fun prepareAdapter() {
        adapter = StepSecondAdapter(viewModel.action)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun isFormValid(): Boolean {
        return adapter.getSelectedItems().filterIsInstance<Car>().isNotEmpty()
    }

    private fun setBottomButtonsListener(shouldShowNextButton: Boolean) {
        binding.primaryButton.isVisible = shouldShowNextButton
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

        if (shouldShowNextButton) {
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
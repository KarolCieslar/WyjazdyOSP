package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentStepThirdBinding
import pl.kcieslar.wyjazdyosp.eventbus.SetCurrentViewPagerItem
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.CarInAction
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddOrEditActionFragment
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddActionViewModel
import pl.kcieslar.wyjazdyosp.views.FunctionNotSelectedDialogView

class StepThirdFragment : Fragment() {

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepThirdBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StepThirdAdapter
    private var selectedCarsList: List<Car> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepThirdBinding.inflate(inflater, container, false)

        setBottomButtonsListener()
        prepareAdapter()

        viewModel.viewModelEvents.observe(viewLifecycleOwner) {
            when (it) {
                is AddActionViewModel.ActionAddedSuccessfully -> {
                    // TODO: Navigate up z parametrem force update który odebrany zostanie w ListActionsFragment i zrobi refresh na liście
                    // TODO: Poprawić selectionStatus bo jest bez sensu. Mozna to zrobić inaczej w Firebase
                    findNavController().navigateUp()
                }
                is AddActionViewModel.CallBackError -> {
                    // TODO: Zrobić snackbara z przyciskiem RETRY i dać refresh calla
                    showSnackBar("Błąd dodawania wyjazdu")
                    Log.e("StepThirdFragment CallBackError", it.exception.toString())
                }
            }
        }

        viewModel.selectedCarsList.observe(viewLifecycleOwner, Observer {
            adapter.setCars(it)
            selectedCarsList = it
        })

        viewModel.forcesList.observe(viewLifecycleOwner, Observer {
            val firemans = it.firemanList.map { fireman -> fireman.copy() }
            adapter.setFiremans(firemans)
        })

        return binding.root
    }

    private fun isFormValid(): Boolean {
        selectedCarsList.forEach { car ->
            val firemansInCar = mutableListOf<Fireman>()
            adapter.getFiremans().forEach { fireman ->
                if (fireman.selectStatus == car.id) {
                    firemansInCar.add(fireman)
                }
            }
            if (firemansInCar.isEmpty()) {
                showSnackBar(resources.getString(R.string.form_none_firemans_in_car))
                return false
            }
        }
        return true
    }

    private fun isEveryCarHasDriverFunctions(): Boolean {
        var checkValue = 0
        selectedCarsList.forEach { car ->
            adapter.getFiremans().forEach { fireman ->
                val firemanFunctions = fireman.functions[car.id]
                if (firemanFunctions?.contains(FiremanFunction.DRIVER) == true) {
                    checkValue++
                }
            }
        }
        return checkValue == selectedCarsList.size
    }

    private fun prepareAdapter() {
        adapter = StepThirdAdapter(binding.recyclerView, viewModel.action)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setText(requireContext().resources.getString(if (viewModel.isEditMode) R.string.button_save_action else R.string.button_add_action))
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                if (!isEveryCarHasDriverFunctions()) {
                    val dialog = FunctionNotSelectedDialogView(requireContext())
                    dialog.setOnPrimaryButtonClickListener {
                        addNewAction()
                    }
                } else {
                    addNewAction()
                }
            }
        }

        binding.cancelButton.setClickListener {
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddOrEditActionFragment.StepNumber.SECOND))
        }
    }

    private fun addNewAction() {
        val carsInAction = mutableListOf<CarInAction>()
        selectedCarsList.forEach { car ->
            val firemansInCar = mutableListOf<Fireman>()
            adapter.getFiremans().forEach { fireman ->
                if (fireman.selectStatus == car.id) {
                    firemansInCar.add(fireman)
                }
            }
            carsInAction.add(CarInAction(car, firemansInCar))
        }
        viewModel.action = viewModel.action.copy(carsInAction = carsInAction)
        if (viewModel.isEditMode) {
            viewModel.editAction(viewModel.action)
        } else {
            viewModel.addAction(viewModel.action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
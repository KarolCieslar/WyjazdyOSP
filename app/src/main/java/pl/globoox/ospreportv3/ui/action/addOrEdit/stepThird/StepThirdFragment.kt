package pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepThirdBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.addOrEdit.AddOrEditActionFragment
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel

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

        viewModel.selectedCarsList.observe(viewLifecycleOwner, Observer {
            adapter.setCars(it)
            selectedCarsList = it
        })

        viewModel.firemanList.observe(viewLifecycleOwner, Observer {
            adapter.setFiremans(it!!)
            // TODO Trzeba ogarnąć to, że ciągle operacje są na tej liveData, trzeba to robić inaczej.
            // TOdo: Podczas dodawanai nowej akcji trzeba dodać również sprzęt bo narazie są same samochody
        })

        return binding.root
    }

    private fun isFormValid(): Boolean {
        selectedCarsList.forEachIndexed { index, _ ->
            val firemansInCar = mutableListOf<Fireman>()
            adapter.getFiremans().forEach { fireman ->
                if (fireman.selectStatus == index) {
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

    private fun prepareAdapter() {
        adapter = StepThirdAdapter(binding.recyclerView)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                addNewAction()
                findNavController().navigateUp()
            }
        }

        binding.cancelButton.setClickListener {
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddOrEditActionFragment.StepNumber.SECOND))
        }
    }

    private fun addNewAction() {
        val carsInAction = mutableListOf<CarInAction>()
        selectedCarsList.forEachIndexed { index, car ->
            val firemansInCar = mutableListOf<Fireman>()
            adapter.getFiremans().forEach { fireman ->
                if (fireman.selectStatus == index) {
                    firemansInCar.add(fireman)
                }
            }
            carsInAction.add(CarInAction(car, firemansInCar))
        }
        viewModel.action = viewModel.action.copy(carsInAction = carsInAction)
        viewModel.addAction(viewModel.action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
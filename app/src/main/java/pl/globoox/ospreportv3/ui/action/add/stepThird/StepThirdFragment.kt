package pl.globoox.ospreportv3.ui.action.add.stepThird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepThirdBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.eventbus.ShowChooseFunctionDialog
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration


class StepThirdFragment : Fragment() {

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepThirdBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StepThirdAdapter
    private lateinit var selectedCarsList: List<Car>

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
            adapter.setFiremans(it)
        })

        return binding.root
    }

    private fun isFormValid(): Boolean {
        return true
    }

    private fun prepareAdapter() {
        adapter = StepThirdAdapter({fireman ->  openFunctionDialog(fireman)}, binding.recyclerView)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin16))
        )
    }

    private fun openFunctionDialog(fireman: Fireman) {
        EventBus.getDefault().post(ShowChooseFunctionDialog(fireman))
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                addNewAction()
                findNavController().navigateUp()
                // TODO(reason = "Upewnić się czy każdy samochód ma jakiegoś firemansa, jeśli nie to dać komuniat aby usunąć dane auto")
            } else {
                showSnackBar(resources.getString(R.string.form_none_cars_selected))
            }
        }

        binding.cancelButton.setClickListener {
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddActionFragment.StepNumber.SECOND))
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
            carsInAction.add(CarInAction(0, car, firemansInCar))
        }
        viewModel.action = viewModel.action.copy(carsInAction = carsInAction)
        viewModel.addAction(viewModel.action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
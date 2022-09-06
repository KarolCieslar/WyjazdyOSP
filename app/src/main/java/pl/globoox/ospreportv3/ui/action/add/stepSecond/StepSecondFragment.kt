package pl.globoox.ospreportv3.ui.action.add.stepSecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepSecondBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.ui.action.add.stepThird.StepThirdAdapter
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration

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

        viewModel.equipmentList.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
        })

        viewModel.carList.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
        })

        setBottomButtonsListener()

        return binding.root
    }

    private fun prepareAdapter() {
        adapter = StepSecondAdapter(onItemClick = { })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin16))
        )
    }

    private fun isFormValid(): Boolean {
        return adapter.getSelectedItems().filterIsInstance<Car>().isNotEmpty()
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                val selectedCars = adapter.getSelectedItems().filterIsInstance<Car>()
                viewModel.setSelectedCars(selectedCars)
                EventBus.getDefault().post(SetCurrentViewPagerItem(AddActionFragment.StepNumber.THIRD))
            } else {
                showSnackBar(resources.getString(R.string.form_none_cars_selected))
            }
        }

        binding.cancelButton.setClickListener {
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddActionFragment.StepNumber.FIRST))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
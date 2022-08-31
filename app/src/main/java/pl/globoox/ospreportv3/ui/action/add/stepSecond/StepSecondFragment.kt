package pl.globoox.ospreportv3.ui.action.add.stepSecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepSecondBinding
import pl.globoox.ospreportv3.eventbus.OnClickNextButtonInAddActionFragment
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.ui.action.add.stepFirst.StepFirstFragment
import pl.globoox.ospreportv3.utils.checkIsNullAndSetError
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration
import java.time.LocalDateTime

class StepSecondFragment : Fragment() {

    private val viewModel: AddActionViewModel by viewModels()
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
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
        )
    }

    private fun isFormValid(): Boolean {
        return adapter.getSelectedItems().filterIsInstance<Car>().isNotEmpty()
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
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
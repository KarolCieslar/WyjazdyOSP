package pl.globoox.ospreportv3.ui.action.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentAddActionBinding
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel
import pl.globoox.ospreportv3.views.StepView

class AddActionFragment : Fragment() {

    private val viewModel: AddActionViewModel by viewModels()
    private var _binding: FragmentAddActionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddActionBinding.inflate(inflater, container, false)

        binding.stepView.setStepsState(listOf(StepView.StepState.CURRENT, StepView.StepState.NEXT, StepView.StepState.NEXT))


//        viewModel.carList.observe(viewLifecycleOwner) {
//            binding.emptyView.isVisible = it.isEmpty()
//            if (it.isEmpty()) binding.emptyView.apply {
//                setMainText(resources.getString(R.string.add_action_empty_view_main))
//                setDescription(resources.getString(R.string.car_fragment_empty_view_description) + " " + resources.getString(R.string.add_action_empty_view_additional_description))
//            }
//        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
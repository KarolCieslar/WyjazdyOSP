package pl.globoox.ospreportv3.ui.action.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentAddActionBinding
import pl.globoox.ospreportv3.ui.action.add.stepFirst.StepFirstFragment
import pl.globoox.ospreportv3.ui.action.add.stepSecond.StepSecondFragment
import pl.globoox.ospreportv3.ui.action.add.stepThird.StepThirdFragment
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel


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

        setCurrentStep(0)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setCurrentStep(step: Int) {
        binding.stepView.setCurrentStep(step)
        val fragment = when (step) {
            0 -> StepFirstFragment()
            1 -> StepSecondFragment()
            else -> StepThirdFragment()
        }

        val ft: FragmentTransaction = childFragmentManager.beginTransaction()
        ft.replace(R.id.stepFrameLayout, fragment)
        ft.commit()
    }
}
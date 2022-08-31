package pl.globoox.ospreportv3.ui.action.add.stepThird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepThirdBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel


class StepThirdFragment : Fragment() {

    private val viewModel: AddActionViewModel by viewModels()
    private var _binding: FragmentStepThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepThirdBinding.inflate(inflater, container, false)

        setBottomButtonsListener()

        return binding.root
    }

    private fun isFormValid(): Boolean {
        return true
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
            EventBus.getDefault().post(SetCurrentViewPagerItem(AddActionFragment.StepNumber.SECOND))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package pl.globoox.ospreportv3.ui.action.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.globoox.ospreportv3.databinding.FragmentAddActionBinding
import pl.globoox.ospreportv3.eventbus.OnClickNextButtonInAddActionFragment
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel


class AddActionFragment : Fragment() {

    private val viewModel: AddActionViewModel by viewModels()
    private var _binding: FragmentAddActionBinding? = null
    private val binding get() = _binding!!
    private var currentStep = StepNumber.FIRST

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddActionBinding.inflate(inflater, container, false)

        setCurrentStep(StepNumber.FIRST)
        setupViewPager()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCurrentStep(step: StepNumber) {
        currentStep = step
        binding.stepView.setCurrentStep(step)
        binding.viewPager.currentItem = step.getIndex()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SetCurrentViewPagerItem?) {
        setCurrentStep(event!!.stepNumber)
    }

    private fun setupViewPager() {
        val adapter = ActionViewPagerAdapter(requireActivity(), 3)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
    }

    enum class StepNumber {
        FIRST, SECOND, THIRD;

        fun getIndex(): Int {
            return when (this) {
                FIRST -> 0
                SECOND -> 1
                THIRD -> 2
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
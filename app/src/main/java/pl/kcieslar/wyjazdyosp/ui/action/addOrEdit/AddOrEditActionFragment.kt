package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.kcieslar.wyjazdyosp.databinding.FragmentAddOrEditActionBinding
import pl.kcieslar.wyjazdyosp.eventbus.SetCurrentViewPagerItem
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.viewmodel.AddActionViewModel
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes


class AddOrEditActionFragment : Fragment() {

    val viewModel: AddActionViewModel by viewModels()
    private val args: AddOrEditActionFragmentArgs by navArgs()
    private var _binding: FragmentAddOrEditActionBinding? = null
    private val binding get() = _binding!!
    private var currentStep = StepNumber.FIRST

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrEditActionBinding.inflate(inflater, container, false)

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
        when (currentStep) {
            StepNumber.FIRST -> setHelpDialogString(HelpDialogStringRes.ADD_ACTION_STEP_ONE)
            StepNumber.SECOND -> setHelpDialogString(HelpDialogStringRes.ADD_ACTION_STEP_SECOND)
            StepNumber.THIRD -> setHelpDialogString(HelpDialogStringRes.ADD_ACTION_STEP_THIRD)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SetCurrentViewPagerItem?) {
        setCurrentStep(event!!.stepNumber)
    }

    private fun setupViewPager() {
        val adapter = ActionViewPagerAdapter(requireActivity(), 3, args.action)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentStep = when (binding.viewPager.currentItem) {
                    0 -> StepNumber.FIRST
                    1 -> StepNumber.SECOND
                    else -> StepNumber.THIRD
                }
                setCurrentStep(currentStep)
                Log.d("dsasad", "onPageSelected")
                super.onPageSelected(position)
            }
        })
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
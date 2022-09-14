package pl.globoox.ospreportv3.ui.action.addOrEdit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.globoox.ospreportv3.databinding.FragmentAddOrEditActionBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.eventbus.ShowChooseFunctionDialog
import pl.globoox.ospreportv3.eventbus.UpdateFiremanFunction
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel


class AddOrEditActionFragment : Fragment() {

    val viewModel: AddActionViewModel by viewModels()
    val args: AddOrEditActionFragmentArgs by navArgs()
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SetCurrentViewPagerItem?) {
        setCurrentStep(event!!.stepNumber)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShowChooseFunctionDialog?) {
        binding.chooseFunctionView.isVisible = true
        binding.darknessView.isVisible = true
        binding.chooseFunctionView.setFireman(event!!.fireman)
        binding.darknessView.setOnClickListener {
            Log.d("asasddsa", "onMessageEvent RUN")
            EventBus.getDefault().post(UpdateFiremanFunction(event.fireman))
            binding.chooseFunctionView.isVisible = false
            binding.darknessView.isVisible = false
        }
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
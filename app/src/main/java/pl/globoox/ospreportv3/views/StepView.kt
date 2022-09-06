package pl.globoox.ospreportv3.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.StepViewBinding
import pl.globoox.ospreportv3.databinding.ViewPrimaryButtonBinding
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment

class StepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = StepViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val stepsDoneIcon: List<ImageView> = listOf(binding.StepFirstIcon, binding.StepSecondIcon, binding.StepThirdIcon)
    private val stepsTextView: List<TextView> = listOf(binding.StepFirstText, binding.StepSecondText, binding.StepThirdText)
    private val stepsCircleView: List<TextView> = listOf(binding.StepFirst, binding.StepSecond, binding.StepThird)

    fun setCurrentStep(step: AddActionFragment.StepNumber) {
        when (step) {
            AddActionFragment.StepNumber.FIRST -> {
                setCurrentState(0)
                setNextState(1)
                setNextState(2)
            }
            AddActionFragment.StepNumber.SECOND -> {
                setDoneState(0)
                setCurrentState(1)
                setNextState(2)
            }
            AddActionFragment.StepNumber.THIRD -> {
                setDoneState(0)
                setDoneState(1)
                setCurrentState(2)
            }
        }
    }

    private fun setCurrentState(step: Int) {
        stepsDoneIcon[step].isVisible = false
        stepsTextView[step].setTypeface(stepsCircleView[step].getTypeface(), Typeface.BOLD)
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.red))
        stepsCircleView[step].setBackgroundResource(R.drawable.step_current)
    }

    private fun setDoneState(step: Int) {
        stepsDoneIcon[step].isVisible = true
        stepsTextView[step].setTypeface(null)
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.white))
        stepsCircleView[step].setBackgroundResource(R.drawable.step_done)
    }

    private fun setNextState(step: Int) {
        stepsDoneIcon[step].isVisible = false
        stepsTextView[step].setTypeface(null)
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.white))
        stepsCircleView[step].setBackgroundResource(R.drawable.step_next)
    }

    enum class StepState {
        DONE, CURRENT, NEXT;
    }
}
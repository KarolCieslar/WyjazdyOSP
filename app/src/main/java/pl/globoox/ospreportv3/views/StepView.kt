package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.StepViewBinding
import pl.globoox.ospreportv3.databinding.ViewPrimaryButtonBinding

class StepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = StepViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val stepsTextView: List<TextView> = listOf(binding.stepOneText, binding.stepTwoText, binding.stepThreeText)
    private val stepsCircleView: List<TextView> = listOf(binding.stepOne, binding.stepThree, binding.stepThree)


    fun setCurrentStep(step: Int) {
        when (step) {
            0 -> {
                setCurrentState(0)
                setNextState(1)
                setNextState(2)
            }
            1 -> {
                setDoneState(0)
                setCurrentState(1)
                setNextState(2)
            }
            2 -> {
                setDoneState(0)
                setDoneState(1)
                setCurrentState(2)
            }
        }
    }

    private fun setCurrentState(step: Int) {
        stepsTextView[step].setTextColor(ContextCompat.getColor(context, R.color.blue))
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.white))
        stepsCircleView[step].setBackgroundResource(ContextCompat.getDrawable(context, R.drawable.step_current))
    }

    private fun setDoneState(step: Int) {
        stepsTextView[step].setTextColor(ContextCompat.getColor(context, R.color.darkBlue))
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.white))
        stepsCircleView[step].setBackgroundResource(ContextCompat.getDrawable(context, R.drawable.step_done))
    }

    private fun setNextState(step: Int) {
        stepsTextView[step].setTextColor(ContextCompat.getColor(context, R.color.darkBlue))
        stepsCircleView[step].setTextColor(ContextCompat.getColor(context, R.color.darkBlue))
        stepsCircleView[step].setBackgroundResource(ContextCompat.getDrawable(context, R.drawable.step_next))
    }

    enum class StepState {
        DONE, CURRENT, NEXT;
    }
}
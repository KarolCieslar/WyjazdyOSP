package pl.globoox.ospreportv3.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.ViewChooseFunctionDialogBinding
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.add.stepThird.FiremanFunction

class ChooseFunctionDialogView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private lateinit var fireman: Fireman
    private val binding = ViewChooseFunctionDialogBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFireman(fireman: Fireman) {
        this.fireman = fireman
        initView()
        setButtonsListener()
    }

    private fun initView() {
        binding.description.text = fireman.name
        if (fireman.functions!!.contains(FiremanFunction.COMMANDER)) {
            binding.commanderButton.setBackgroundResource(R.drawable.function_selected_button_background)
        } else {
            binding.commanderButton.setBackgroundResource(R.drawable.function_not_selected_button_background)
        }

        if (fireman.functions!!.contains(FiremanFunction.DRIVER)) {
            binding.driverButton.setBackgroundResource(R.drawable.function_selected_button_background)
        } else {
            binding.driverButton.setBackgroundResource(R.drawable.function_not_selected_button_background)
        }

        if (fireman.functions!!.contains(FiremanFunction.OWNCAR)) {
            binding.ownCarButton.setBackgroundResource(R.drawable.function_selected_button_background)
        } else {
            binding.ownCarButton.setBackgroundResource(R.drawable.function_not_selected_button_background)
        }
    }

    private fun setButtonsListener() {
        binding.commanderButton.setOnClickListener {
            handleButtonsClick(it, FiremanFunction.COMMANDER)
        }
        binding.driverButton.setOnClickListener {
            handleButtonsClick(it, FiremanFunction.DRIVER)
        }
        binding.ownCarButton.setOnClickListener {
            handleButtonsClick(it, FiremanFunction.OWNCAR)
        }
    }

    private fun handleButtonsClick(view: View, firemanFunction: FiremanFunction) {
        if (fireman.functions!!.contains(firemanFunction)) {
            view.setBackgroundResource(R.drawable.function_not_selected_button_background)
            fireman.functions!!.remove(firemanFunction)
        } else {
            view.setBackgroundResource(R.drawable.function_selected_button_background)
            fireman.functions!!.add(firemanFunction)
        }
    }
}
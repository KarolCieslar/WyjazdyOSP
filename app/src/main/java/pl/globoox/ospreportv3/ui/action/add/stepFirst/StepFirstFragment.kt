package pl.globoox.ospreportv3.ui.action.add.stepFirst

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import pl.globoox.ospreportv3.databinding.FragmentStepFirstBinding
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class StepFirstFragment : Fragment() {

    companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT)
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
    }

    private val viewModel: AddActionViewModel by viewModels()
    private var _binding: FragmentStepFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepFirstBinding.inflate(inflater, container, false)

        setCurrentInOutDate()
        setInOutDateTimeClickListener()
        setBottomButtonListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBottomButtonListener() {
        binding.primaryButton.setClickListener {
            val parentFrag: AddActionFragment = this.parentFragment as AddActionFragment
            parentFrag.setCurrentStep(1)
        }
    }

    private fun setInOutDateTimeClickListener() {
        binding.etOutDate.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { openDatePicker(binding.etOutDate) }
        }
        binding.etOutTime.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { openTimePicker(binding.etOutTime) }
        }
        binding.etInDate.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { openDatePicker(binding.etInDate) }
        }
        binding.etInTime.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { openTimePicker(binding.etInTime) }
        }
    }

    private fun setCurrentInOutDate() {
        binding.etOutDate.setText(LocalDateTime.now().format(dateFormatter))
        binding.etOutTime.setText(LocalTime.now().format(timeFormatter))
        binding.etInDate.setText(LocalDateTime.now().format(dateFormatter))
        binding.etInTime.setText(LocalTime.now().format(timeFormatter))
    }

    private fun openDatePicker(view: AppCompatEditText) {
        val date = LocalDateTime.parse("${view.text} 00:00", dateFormatterHelper)
        val zdt: ZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
        val datePicker = MaterialDatePicker
            .Builder.datePicker()
            .setSelection(zdt.toInstant().toEpochMilli())
            .build()
        datePicker.addOnPositiveButtonClickListener { selectedDateMilli ->
            val selectedDate = Instant.ofEpochMilli(selectedDateMilli).atZone(ZoneId.systemDefault()).toLocalDate().format(dateFormatter)
            view.setText(selectedDate.toString())
        }
        datePicker.show(childFragmentManager, "Wybierz datę")
    }

    private fun openTimePicker(view: AppCompatEditText) {
        val defaultValue = LocalTime.parse(view.text, timeFormatter)
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(defaultValue.hour)
            .setMinute(defaultValue.minute)
            .build()
        timePicker.addOnPositiveButtonClickListener {
            val newHour: Int = timePicker.hour
            val newMinute: Int = timePicker.minute
            view.setText(String.format("%02d:%02d", newHour, newMinute));
        }
        timePicker.show(childFragmentManager, "Wybierz godzinę")
    }
}
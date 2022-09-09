package pl.globoox.ospreportv3.ui.action.add.stepFirst

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.greenrobot.eventbus.EventBus
import pl.globoox.ospreportv3.MainActivity
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepFirstBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.utils.checkIsNullAndSetError
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.DateTimeFormFieldView
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


class StepFirstFragment : Fragment() {

    companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ROOT)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT)
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
    }

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepFirstBinding? = null // TODO: PRzebudować bo za długo się ładuje
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepFirstBinding.inflate(inflater, container, false)

        binding.etLocation.setText("TESTOWA LOKACJA")
        binding.etRaportNumber.setText("12321321A/AF/WA")

        setCurrentInOutDate()
        setInOutDateTimeClickListener()
        setBottomButtonListener()

        return binding.root
    }


    private fun setBottomButtonListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                viewModel.action = viewModel.action.copy(
                    id = 0,
                    outTime = "${binding.outDate.getValue()} ${binding.outTime.getValue()}",
                    inTime = "${binding.inDate.getValue()} ${binding.inTime.getValue()}",
                    location = binding.etLocation.text.toString(),
                    number = binding.etRaportNumber.text.toString(),
                    description = binding.etDescription.text.toString()
                )
                EventBus.getDefault().post(SetCurrentViewPagerItem(AddActionFragment.StepNumber.SECOND))
            }
        }
    }

    private fun isFormValid(): Boolean {
        val errorList: MutableList<Boolean> = mutableListOf()
        errorList.add(binding.etLocation.checkIsNullAndSetError(resources.getString(R.string.field_empty)))
        errorList.add(binding.etRaportNumber.checkIsNullAndSetError(resources.getString(R.string.field_empty)))

        val outDate = LocalDateTime.parse("${binding.outDate.getValue()} ${binding.outTime.getValue()}", dateFormatterHelper)
        val inDate = LocalDateTime.parse("${binding.inDate.getValue()} ${binding.inTime.getValue()}", dateFormatterHelper)
        if (inDate.isBefore(outDate)){
            showSnackBar(resources.getString(R.string.form_date_range_error))
            errorList.add(true)
        }

        return errorList.filter { it == true}.isEmpty()
    }

    private fun setInOutDateTimeClickListener() {
        binding.outDate.apply {
            setOnClickListener { openDatePicker(binding.outDate) }
        }
        binding.outTime.apply {
            setOnClickListener { openTimePicker(binding.outTime) }
        }
        binding.inDate.apply {
            setOnClickListener { openDatePicker(binding.inDate) }
        }
        binding.inTime.apply {
            setOnClickListener { openTimePicker(binding.inTime) }
        }
    }

    private fun setCurrentInOutDate() {
        binding.outDate.setValue(LocalDateTime.now().format(dateFormatter))
        binding.outTime.setValue(LocalTime.now().format(timeFormatter))
        binding.inDate.setValue(LocalDateTime.now().format(dateFormatter))
        binding.inTime.setValue(LocalTime.now().format(timeFormatter))
    }

    private fun openDatePicker(view: DateTimeFormFieldView) {
        val date = LocalDateTime.parse("${view.getValue()} 00:00", dateFormatterHelper)
        val zdt: ZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
        val datePicker = MaterialDatePicker
            .Builder.datePicker()
            .setSelection(zdt.toInstant().toEpochMilli())
            .build()
        datePicker.addOnPositiveButtonClickListener { selectedDateMilli ->
            val selectedDate = Instant.ofEpochMilli(selectedDateMilli).atZone(ZoneId.systemDefault()).toLocalDate().format(dateFormatter)
            view.setValue(selectedDate.toString())
        }
        datePicker.show(childFragmentManager, "Wybierz datę")
    }

    private fun openTimePicker(view: DateTimeFormFieldView) {
        val defaultValue = LocalTime.parse(view.getValue(), timeFormatter)
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(defaultValue.hour)
            .setMinute(defaultValue.minute)
            .build()
        timePicker.addOnPositiveButtonClickListener {
            val newHour: Int = timePicker.hour
            val newMinute: Int = timePicker.minute
            view.setValue(String.format("%02d:%02d", newHour, newMinute));
        }
        timePicker.show(childFragmentManager, "Wybierz godzinę")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
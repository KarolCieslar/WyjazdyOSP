package pl.globoox.ospreportv3.ui.action.addOrEdit.stepFirst

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.greenrobot.eventbus.EventBus
import pl.globoox.ospreportv3.MainActivity.Companion.dateFormatter
import pl.globoox.ospreportv3.MainActivity.Companion.dateFormatterHelper
import pl.globoox.ospreportv3.MainActivity.Companion.timeFormatter
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepFirstBinding
import pl.globoox.ospreportv3.eventbus.SetCurrentViewPagerItem
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.ui.action.addOrEdit.AddOrEditActionFragment
import pl.globoox.ospreportv3.utils.checkIsNullAndSetError
import pl.globoox.ospreportv3.utils.setHelpDialogString
import pl.globoox.ospreportv3.utils.showSnackBar
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.DateTimeFormFieldView
import pl.globoox.ospreportv3.views.HelpDialogStringRes
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


class StepFirstFragment(
    val action: Action? = null
) : Fragment() {

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepFirstBinding? = null // TODO: PRzebudować bo za długo się ładuje
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepFirstBinding.inflate(inflater, container, false)
        viewModel.isEditMode = action != null
        if (action != null) {
            binding.etLocation.setText(action.location)
            binding.etRaportNumber.setText(action.number)
            binding.etDescription.setText(action.description)
            binding.outDate.setValue(action.outTime.split(" ")[0])
            binding.outTime.setValue(action.outTime.split(" ")[1])
            binding.inDate.setValue(action.inTime.split(" ")[0])
            binding.inTime.setValue(action.inTime.split(" ")[1])
            viewModel.action = action
        } else {
            viewModel.action = Action(0, LocalDateTime.now().toString(), LocalDateTime.now().toString(), "", "", null, emptyList(), emptyList())
            setCurrentInOutDate()
        }
        setInOutDateTimeClickListener()
        setBottomButtonListener()

        return binding.root
    }


    private fun setBottomButtonListener() {
        binding.primaryButton.setClickListener {
            if (isFormValid()) {
                viewModel.action = viewModel.action.copy(
                    outTime = "${binding.outDate.getValue()} ${binding.outTime.getValue()}",
                    inTime = "${binding.inDate.getValue()} ${binding.inTime.getValue()}",
                    location = binding.etLocation.text.toString(),
                    number = binding.etRaportNumber.text.toString(),
                    description = binding.etDescription.text.toString()
                )
                EventBus.getDefault().post(SetCurrentViewPagerItem(AddOrEditActionFragment.StepNumber.SECOND))
            }
        }

        binding.cancelButton.setClickListener {
            viewModel.addAction(
                Action(
                    (9000..10000).random(),
                    "01.01.2022 ${(10..19).random()}:${(10..56).random()}",
                    "02.01.2022 ${(10..19).random()}:${(10..56).random()}",
                    "Lokacja jakas ${(9000..10000).random()}",
                    "${(9000..10000).random()}",
                    "${(9000..10000).random()}",
                    listOf(
                        CarInAction(
                            Car((9000..10000).random(), "${(9000..10000).random()}"),
                            listOf(
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}")
                            )
                        ),
                        CarInAction(
                            Car((9000..10000).random(), "${(9000..10000).random()}"),
                            listOf(
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}"),
                                Fireman((9000..10000).random(), "${(9000..10000).random()}")
                            )
                        )
                    ),
                    emptyList()
                )
            )
//            findNavController().navigateUp()
        }
    }

    private fun isFormValid(): Boolean {
        val errorList: MutableList<Boolean> = mutableListOf()
        errorList.add(binding.etLocation.checkIsNullAndSetError(resources.getString(R.string.field_empty)))
        errorList.add(binding.etRaportNumber.checkIsNullAndSetError(resources.getString(R.string.field_empty)))

        val outDate = LocalDateTime.parse("${binding.outDate.getValue()} ${binding.outTime.getValue()}", dateFormatterHelper)
        val inDate = LocalDateTime.parse("${binding.inDate.getValue()} ${binding.inTime.getValue()}", dateFormatterHelper)
        if (inDate.isBefore(outDate) || inDate.isEqual(outDate)) {
            showSnackBar(resources.getString(R.string.form_date_range_error))
            errorList.add(true)
        }

        return errorList.filter { it == true }.isEmpty()
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
        binding.inTime.setValue(LocalTime.now().plusHours(1).format(timeFormatter))
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
            view.setValue(String.format("%02d:%02d", newHour, newMinute))
        }
        timePicker.show(childFragmentManager, "Wybierz godzinę")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
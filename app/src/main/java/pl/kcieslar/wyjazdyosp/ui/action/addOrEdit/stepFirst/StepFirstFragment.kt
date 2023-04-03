package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepFirst

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import pl.kcieslar.wyjazdyosp.databinding.FragmentStepFirstBinding
import org.greenrobot.eventbus.EventBus
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatter
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatterHelper
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.timeFormatter
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.eventbus.SetCurrentViewPagerItem
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddOrEditActionFragment
import pl.kcieslar.wyjazdyosp.utils.checkIsNullAndSetError
import pl.kcieslar.wyjazdyosp.utils.showSnackBar
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.AddActionViewModel
import pl.kcieslar.wyjazdyosp.views.DateTimeFormFieldView
import java.time.*


class StepFirstFragment(
    val action: Action? = null
) : Fragment() {

    private val viewModel: AddActionViewModel by activityViewModels()
    private var _binding: FragmentStepFirstBinding? = null
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
            findNavController().navigateUp()
        }
    }

    private fun isFormValid(): Boolean {
        val errorList: MutableList<Boolean> = mutableListOf()
        errorList.add(binding.etLocation.checkIsNullAndSetError(resources.getString(R.string.field_empty)))

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
        val currentInDate = when (LocalTime.now().hour) {
            23 -> LocalDateTime.now().plusDays(1)
            else -> LocalDateTime.now()
        }
        binding.inDate.setValue(currentInDate.format(dateFormatter))
        binding.inTime.setValue(LocalTime.now().plusHours(1).format(timeFormatter))
    }

    private fun openDatePicker(view: DateTimeFormFieldView) {
        val date = LocalDateTime.parse("${view.getValue()} 15:00", dateFormatterHelper)
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

    private fun openTimePicker(dateFormView: DateTimeFormFieldView) {
        val defaultValue = LocalTime.parse(dateFormView.getValue(), timeFormatter)
        TimePickerDialog(context, TimePickerDialog.THEME_HOLO_LIGHT, { _, hourOfDay, minute ->
            dateFormView.setValue(String.format("%02d:%02d", hourOfDay, minute))
        }, defaultValue.hour, defaultValue.minute, true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
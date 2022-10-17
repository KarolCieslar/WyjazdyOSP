package pl.kcieslar.wyjazdyosp.ui.salary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.zhuinden.livedatacombinetuplekt.combineTuple
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatter
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatterHelper
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.databinding.FragmentSalaryBinding
import pl.kcieslar.wyjazdyosp.model.Quarter
import pl.kcieslar.wyjazdyosp.utils.convertStringToLocalDateTime
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.viewmodel.SalaryViewModel
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes
import pl.kcieslar.wyjazdyosp.views.SalaryValueDialogView
import java.time.*
import java.time.temporal.IsoFields
import java.util.*


class SalaryFragment : Fragment() {

    private val viewModel: SalaryViewModel by viewModels()
    private var _binding: FragmentSalaryBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreferences? = null
    private var salaryPerHour = -1
    private val adapter = SalaryAdapter()
    private lateinit var selectedQuarter: Quarter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalaryBinding.inflate(inflater, container, false)
        sharedPref = activity?.getSharedPreferences("SALARY_HOUR_VALUE", Context.MODE_PRIVATE)
        selectedQuarter = Quarter("Default Quarter", Calendar.getInstance().get(Calendar.YEAR), LocalDate.now().get(IsoFields.QUARTER_OF_YEAR))

        getNewSalaryValue()
        buildFragmentUI(selectedQuarter)
        initSpinner()
        setDateClickListener()
        setDefaultFromToDate()
        changeSelectDateState(viewModel.dateButtonSelected)

        binding.openDateButton.setOnClickListener {
            viewModel.dateButtonSelected = !viewModel.dateButtonSelected
            changeSelectDateState(viewModel.dateButtonSelected)
            binding.quarterSelect.isEnabled = !viewModel.dateButtonSelected
            buildFragmentUI(if (!viewModel.dateButtonSelected) selectedQuarter else null)
        }

        setHelpDialogString(HelpDialogStringRes.SALARY)
        return binding.root
    }

    private fun initSpinner() {
        val provinceList: MutableList<Quarter> = mutableListOf()
        provinceList.add(Quarter("PAZ - GRU - 2023", 2023, 4))
        provinceList.add(Quarter("LIP - WRZ - 2023", 2023, 3))
        provinceList.add(Quarter("KWI - CZE - 2023", 2023, 2))
        provinceList.add(Quarter("STY - MAR - 2023", 2023, 1))
        provinceList.add(Quarter("PAZ - GRU - 2022", 2022, 4))

        binding.quarterSelect.item = provinceList.map { it.name }

        binding.quarterSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                buildFragmentUI(provinceList[position])
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        val selectedItem = provinceList.indexOfFirst { selectedQuarter.quarter == it.quarter && selectedQuarter.year == it.year }
        binding.quarterSelect.setSelection(selectedItem)
    }

    private fun changeSelectDateState(dateButtonSelected: Boolean) {
        binding.openDateButton.setBackgroundResource(if (!dateButtonSelected) R.drawable.open_date_select_button_drawable else R.drawable.open_date_select_button_drawable_selected)
        binding.fromDate.isVisible = dateButtonSelected
        binding.toDate.isVisible = dateButtonSelected
        binding.boxForQuarter.alpha = if (dateButtonSelected) 0.3f else 1f
        binding.quarterSelect.apply {
            alpha = if (dateButtonSelected) 0.3f else 1f
            isClickable = !dateButtonSelected
        }
    }

    private fun setDefaultFromToDate() {
        binding.fromDate.setValue(LocalDateTime.now().minusDays(30).format(dateFormatter))
        binding.toDate.setValue(LocalDateTime.now().format(dateFormatter))
    }

    private fun buildFragmentUI(selectedQuarter: Quarter? = null) {
        binding.salaryValueEdit.setOnClickListener {
            openSalaryValueEditDialog(salaryPerHour)
        }

        if (salaryPerHour == -1) {
            binding.viewGroup.isVisible = false
            binding.emptyView.apply {
                isVisible = true
                setMainText(resources.getString(R.string.salary_fragment_salary_not_set))
                setDescription(resources.getString(R.string.salary_fragment_salary_not_set_description))
                setButtonData("Ustal Stawkę") { openSalaryValueEditDialog(0) }
            }
        } else {
            binding.viewGroup.isVisible = false
            val recyclerView = binding.recyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            while (recyclerView.itemDecorationCount > 0) {
                recyclerView.removeItemDecorationAt(0)
            }

            val horizontalDecoration = DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
            val horizontalDivider = ContextCompat.getDrawable(requireContext(), R.drawable.horizontal_divider_line)
            horizontalDecoration.setDrawable(horizontalDivider!!)
            recyclerView.addItemDecoration(horizontalDecoration)

            adapter.setSalaryValue(salaryPerHour)

            combineTuple(viewModel.firemanList, viewModel.firemanActions).observe(viewLifecycleOwner) { (firemans, actions) ->
                if (firemans != null && actions != null) {
                    binding.emptyView.isVisible = firemans.isEmpty()
                    binding.viewGroup.isVisible = firemans.isNotEmpty()
                    changeSelectDateState(viewModel.dateButtonSelected)
                    if (firemans.isEmpty()) binding.emptyView.apply {
                        setMainText(resources.getString(R.string.fireman_fragment_empty_view_main))
                        setDescription(resources.getString(R.string.fireman_fragment_empty_view_description))
                        setButtonData(resources.getString(R.string.fireman_fragment_empty_view_button)) {
                            findNavController().navigate(SalaryFragmentDirections.actionSalaryFragmentToForcesFragment(1, true))
                        }
                    }

                    val filteredActions = actions.filter {
                        val date = convertStringToLocalDateTime(it.outTime).toLocalDate()
                        if (viewModel.dateButtonSelected) {
                            val fromDate = LocalDate.parse(binding.fromDate.getValue(), dateFormatter)
                            val toDate = LocalDate.parse(binding.toDate.getValue(), dateFormatter)
                            date.isEqual(toDate) || date.isEqual(fromDate) || date.isAfter(fromDate) && date.isBefore(toDate)
                        } else {
                            date.year == selectedQuarter?.year && date.get(IsoFields.QUARTER_OF_YEAR) == selectedQuarter.quarter
                        }
                    }
                    adapter.setData(firemans, filteredActions)
                }
            }
        }
    }

    private fun setDateClickListener() {
        binding.fromDate.setOnClickListener { openFromDatePicker() }
        binding.toDate.setOnClickListener { openToDatePicker() }
    }

    private fun openToDatePicker() {
        val fromDate = LocalDateTime.parse("${binding.fromDate.getValue()} 15:00", dateFormatterHelper)
        val fromDateZdt: ZonedDateTime = ZonedDateTime.of(fromDate, ZoneId.systemDefault())
        val constraint = CalendarConstraints.Builder().setValidator(DateValidatorPointForward.from(fromDateZdt.toInstant().toEpochMilli()))
        val date = LocalDateTime.parse("${binding.toDate.getValue()} 15:00", dateFormatterHelper)
        val zdt: ZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
        val datePicker = MaterialDatePicker
            .Builder.datePicker()
            .setSelection(zdt.toInstant().toEpochMilli())
            .setCalendarConstraints(constraint.build()).build()
        datePicker.addOnPositiveButtonClickListener { selectedDateMilli ->
            val selectedDate = Instant.ofEpochMilli(selectedDateMilli).atZone(ZoneId.systemDefault()).toLocalDate().format(dateFormatter)
            binding.toDate.setValue(selectedDate.toString())
            buildFragmentUI()
        }
        datePicker.show(childFragmentManager, "Wybierz datę")
    }

    private fun openFromDatePicker() {
        val toDate = LocalDateTime.parse("${binding.toDate.getValue()} 15:00", dateFormatterHelper)
        val toDateZdt: ZonedDateTime = ZonedDateTime.of(toDate, ZoneId.systemDefault())
        val constraint = CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.before(toDateZdt.toInstant().toEpochMilli()))
        val date = LocalDateTime.parse("${binding.fromDate.getValue()} 15:00", dateFormatterHelper)
        val zdt: ZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault())
        val datePicker = MaterialDatePicker
            .Builder.datePicker()
            .setSelection(zdt.toInstant().toEpochMilli())
            .setCalendarConstraints(constraint.build()).build()
        datePicker.addOnPositiveButtonClickListener { selectedDateMilli ->
            val selectedDate = Instant.ofEpochMilli(selectedDateMilli).atZone(ZoneId.systemDefault()).toLocalDate().format(dateFormatter)
            binding.fromDate.setValue(selectedDate.toString())
            buildFragmentUI()
        }
        datePicker.show(childFragmentManager, "Wybierz datę")
    }

    private fun openSalaryValueEditDialog(salaryPerHour: Int) {
        val dialog = SalaryValueDialogView(requireContext(), salaryPerHour)
        dialog.setOnPrimaryButtonClickListener { selectedValue ->
            sharedPref?.edit()?.putInt("SALARY_HOUR_VALUE", selectedValue)?.apply()
            getNewSalaryValue()
            adapter.setSalaryValue(salaryPerHour)
            buildFragmentUI(selectedQuarter)
        }
    }

    private fun getNewSalaryValue() {
        salaryPerHour = sharedPref?.getInt("SALARY_HOUR_VALUE", -1) ?: -1
    }
}
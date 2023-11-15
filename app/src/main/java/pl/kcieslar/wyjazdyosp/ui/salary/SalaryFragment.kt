package pl.kcieslar.wyjazdyosp.ui.salary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatter
import pl.kcieslar.wyjazdyosp.MainActivity.Companion.dateFormatterHelper
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseFragment
import pl.kcieslar.wyjazdyosp.databinding.FragmentSalaryBinding
import pl.kcieslar.wyjazdyosp.model.Quarter
import pl.kcieslar.wyjazdyosp.utils.convertStringToLocalDateTime
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes
import pl.kcieslar.wyjazdyosp.views.SalaryValueDialogView
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.IsoFields
import java.util.Calendar

@AndroidEntryPoint
class SalaryFragment : BaseFragment<FragmentSalaryBinding, SalaryViewModel>() {

    override val layoutId: Int = R.layout.fragment_salary
    override val viewModel: SalaryViewModel by viewModels()

    private var sharedPref: SharedPreferences? = null
    private var salaryPerHour = -1
    private val adapter = SalaryAdapter()
    private lateinit var selectedQuarter: Quarter

    companion object {
        private var dateButtonSelected: Boolean = false
    }

    override fun onReady(savedInstanceState: Bundle?) {
        sharedPref = activity?.getSharedPreferences("SHARED_PREF_APP_OSP", Context.MODE_PRIVATE)
        selectedQuarter = Quarter("Default Quarter", Calendar.getInstance()[(Calendar.YEAR)], LocalDate.now()[IsoFields.QUARTER_OF_YEAR])

        showShimmer(true)
        getNewSalaryValue()
        buildFragmentUI(selectedQuarter)
        initSpinner()
        setDateClickListener()
        setDefaultFromToDate()
        changeSelectDateState(dateButtonSelected)

        binding.openDateButton.setOnClickListener {
            dateButtonSelected = !dateButtonSelected
            changeSelectDateState(dateButtonSelected)
            binding.quarterSelect.isEnabled = !dateButtonSelected
            buildFragmentUI(if (!dateButtonSelected) selectedQuarter else null)
        }

        setHelpDialogString(HelpDialogStringRes.SALARY)
    }

    private fun initSpinner() {
        val quarterList: MutableList<Quarter> = mutableListOf()
        quarterList.add(Quarter("PAZ - GRU - 2024", 2024, 4))
        quarterList.add(Quarter("LIP - WRZ - 2024", 2024, 3))
        quarterList.add(Quarter("KWI - CZE - 2024", 2024, 2))
        quarterList.add(Quarter("STY - MAR - 2024", 2024, 1))
        quarterList.add(Quarter("PAZ - GRU - 2023", 2023, 4))
        quarterList.add(Quarter("LIP - WRZ - 2023", 2023, 3))
        quarterList.add(Quarter("KWI - CZE - 2023", 2023, 2))
        quarterList.add(Quarter("STY - MAR - 2023", 2023, 1))
        quarterList.add(Quarter("PAZ - GRU - 2022", 2022, 4))

        binding.quarterSelect.item = quarterList.map { it.name }

        binding.quarterSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                buildFragmentUI(quarterList[position])
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // Sonar
            }
        }

        val selectedItem = quarterList.indexOfFirst { selectedQuarter.quarter == it.quarter && selectedQuarter.year == it.year }
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

    private fun showShimmer(show: Boolean) {
        binding.shimmerFrameLayout.apply {
            if (show) startShimmerAnimation() else stopShimmerAnimation()
            isVisible = show
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
            showShimmer(false)
            binding.viewGroup.isVisible = false
            binding.errorView.apply {
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
            handleSalaryViewData(selectedQuarter)
        }
    }

    private fun handleSalaryViewData(selectedQuarter: Quarter?) {
        combineTuple(viewModel.forces, viewModel.actions).observe(viewLifecycleOwner) { (forces, actions) ->
            if (forces != null && actions != null) {
                showShimmer(false)
                val firemans = forces.getFiremanList()
                binding.errorView.isVisible = firemans.isEmpty()
                binding.viewGroup.isVisible = firemans.isNotEmpty()
                changeSelectDateState(dateButtonSelected)
                if (firemans.isEmpty()) binding.errorView.apply {
                    setMainText(resources.getString(R.string.fireman_fragment_empty_view_main))
                    setDescription(resources.getString(R.string.fireman_fragment_empty_view_description))
                    setButtonData(resources.getString(R.string.fireman_fragment_empty_view_button)) {
                        findNavController().navigate(SalaryFragmentDirections.actionSalaryFragmentToForcesFragment(1, true))
                    }
                }

                val filteredActions = actions.list?.filter {
                    val date = convertStringToLocalDateTime(it.outTime).toLocalDate()
                    if (dateButtonSelected) {
                        val fromDate = LocalDate.parse(binding.fromDate.getValue(), dateFormatter)
                        val toDate = LocalDate.parse(binding.toDate.getValue(), dateFormatter)
                        date.isEqual(toDate) || date.isEqual(fromDate) || date.isAfter(fromDate) && date.isBefore(toDate)
                    } else {
                        date.year == selectedQuarter?.year && date[IsoFields.QUARTER_OF_YEAR] == selectedQuarter.quarter
                    }
                } ?: listOf()
                adapter.setData(firemans, filteredActions)
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
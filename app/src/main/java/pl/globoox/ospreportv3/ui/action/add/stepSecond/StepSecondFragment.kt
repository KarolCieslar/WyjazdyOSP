package pl.globoox.ospreportv3.ui.action.add.stepSecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentStepSecondBinding
import pl.globoox.ospreportv3.ui.action.add.AddActionFragment
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel
import pl.globoox.ospreportv3.views.MarginItemDecoration

class StepSecondFragment : Fragment() {

    private val viewModel: AddActionViewModel by viewModels()
    private var _binding: FragmentStepSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepSecondBinding.inflate(inflater, container, false)

        val adapter = StepSecondAdapter(onItemClick = { })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin10))
        )

        viewModel.equipmentList.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
        })

        viewModel.carList.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
        })

        setBottomButtonsListener()


        return binding.root
    }

    private fun setBottomButtonsListener() {
        binding.primaryButton.setClickListener {
            val parentFrag: AddActionFragment = this.parentFragment as AddActionFragment
            parentFrag.setCurrentStep(2)
        }

        binding.cancelButton.setClickListener {
            val parentFrag: AddActionFragment = this.parentFragment as AddActionFragment
            parentFrag.setCurrentStep(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
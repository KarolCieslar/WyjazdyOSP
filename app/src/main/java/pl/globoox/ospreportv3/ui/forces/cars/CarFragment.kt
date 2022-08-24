package pl.globoox.ospreportv3.ui.forces.cars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentForcesCarsListBinding
import pl.globoox.ospreportv3.ui.forces.fireman.FiremanListAdapter
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel

class CarFragment : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesCarsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesCarsListBinding.inflate(inflater, container, false)

        val adapter = CarListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.carList.observe(viewLifecycleOwner, Observer {
            binding.emptyView.isVisible = it.isNotEmpty()
            if (it.isNotEmpty()) binding.emptyView.apply {
                setMainText(resources.getString(R.string.car_fragment_empty_view_main))
                setDescription(resources.getString(R.string.car_fragment_empty_view_description))
                setButtonData(resources.getString(R.string.car_fragment_empty_view_button), {})
            }
            adapter.setData(it)
        })

        binding.floatingActionButton.setOnClickListener {
        }
        return binding.root
    }

//    private fun addNewFireman() {
//        val firstName = binding.etName.text.toString()
//        val lastName = binding.etSurname.text.toString()
//        val fireman = Fireman(0, firstName, lastName)
//        viewModel.addFireman(fireman)
//        findNavController().navigateUp()
//    }

//    private fun updateFireman() {
//        val firstName = binding.etName.text.toString()
//        val lastName = binding.etSurname.text.toString()
//        val fireman = Fireman(0, firstName, lastName)
//        viewModel.addFireman(fireman)
//        findNavController().navigateUp()
//    }
}
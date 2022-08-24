package pl.globoox.ospreportv3.ui.forces.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import pl.globoox.ospreportv3.databinding.FragmentForcesEquipmentListBinding
import pl.globoox.ospreportv3.ui.forces.fireman.FiremanListAdapter
import pl.globoox.ospreportv3.viewmodel.ForcesViewModel

class EquipmentFragment : Fragment() {

    private val viewModel: ForcesViewModel by viewModels()
    private var _binding: FragmentForcesEquipmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesEquipmentListBinding.inflate(inflater, container, false)

        val adapter = EquipmentListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.equipmentList.observe(viewLifecycleOwner, Observer {
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
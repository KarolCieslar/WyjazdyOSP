package pl.globoox.ospreportv3.ui.forces.fireman.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pl.globoox.ospreportv3.viewmodel.FiremanViewModel
import pl.globoox.ospreportv3.databinding.FragmentAddFiremanBinding
import pl.globoox.ospreportv3.model.Fireman

class AddFireman : Fragment() {

    private var _binding: FragmentAddFiremanBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFiremanViewModel: FiremanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFiremanBinding.inflate(inflater, container, false)

        mFiremanViewModel = ViewModelProvider(this).get(FiremanViewModel::class.java)

        binding.button.setOnClickListener {
            addNewFireman()
        }

        return binding.root
    }

    private fun addNewFireman() {
        val firstName = binding.etName.text.toString()
        val lastName = binding.etSurname.text.toString()
        val fireman = Fireman(0, firstName, lastName)
        mFiremanViewModel.addFireman(fireman)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
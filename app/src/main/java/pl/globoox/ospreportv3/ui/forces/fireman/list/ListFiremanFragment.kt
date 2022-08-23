package pl.globoox.ospreportv3.ui.forces.fireman.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentListFiremanBinding

class ListFiremanFragment : Fragment() {

    private var _binding: FragmentListFiremanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFiremanBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFiremanFragment_to_addFireman)
        }
        return binding.root
    }
}
package pl.kcieslar.wyjazdyosp.ui.backup_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.databinding.FragmentBackupRoomBinding

@AndroidEntryPoint
class BackupRoomFragment : Fragment() {

    private val viewModel: BackupRoomViewModel by viewModels()
    private var _binding: FragmentBackupRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackupRoomBinding.inflate(inflater, container, false)
        setMenuVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.primaryButton.setOnClickListener {
            viewModel.getBackup()
        }
    }
}
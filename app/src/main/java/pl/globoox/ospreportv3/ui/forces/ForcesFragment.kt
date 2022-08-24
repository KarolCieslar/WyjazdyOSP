package pl.globoox.ospreportv3.ui.forces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import pl.globoox.ospreportv3.databinding.FragmentForcesBinding

class ForcesFragment : Fragment() {

    private var _binding: FragmentForcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesBinding.inflate(inflater, container, false)

        setupViewPager()
        setupTabLayout()

        return binding.root
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position -> tab.text = "Tab " + (position + 1) }.attach()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(requireActivity(), 3)
        binding.viewPager.adapter = adapter
    }
}
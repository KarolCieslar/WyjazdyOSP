package pl.kcieslar.wyjazdyosp.ui.forces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import com.google.android.material.tabs.TabLayoutMediator
import pl.kcieslar.wyjazdyosp.databinding.FragmentForcesBinding
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes

class ForcesFragment : Fragment() {

    private val args: ForcesFragmentArgs by navArgs()
    private var _binding: FragmentForcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForcesBinding.inflate(inflater, container, false)

        setHelpDialogString(HelpDialogStringRes.FORCES)
        setupViewPager()
        setupTabLayout()

        return binding.root
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabText = when (position) {
                0 -> "Pojazdy"
                1 -> "Ratownicy"
                else -> "Sprzet"
            }
            tab.text = tabText }.attach()
        selectPage(args.defaultTab)
    }

    private fun selectPage(pageIndex: Int) {
        binding.tabLayout.setScrollPosition(pageIndex, 0f, true)
        binding.viewPager.currentItem = pageIndex
    }

    private fun setupViewPager() {
        val adapter = ForcesViewPagerAdapter(this, 3, openAddDialogAtInit = args.openAddDialogAtInit)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }
}
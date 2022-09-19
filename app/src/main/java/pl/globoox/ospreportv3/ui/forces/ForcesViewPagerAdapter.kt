package pl.globoox.ospreportv3.ui.forces

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.globoox.ospreportv3.ui.forces.view.ViewPagerFragment

class ForcesViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ViewPagerFragment(ForcesDataType.CAR)
            1 -> ViewPagerFragment(ForcesDataType.FIREMAN)
            else -> ViewPagerFragment(ForcesDataType.EQUIPMENT)
        }
    }

    enum class ForcesDataType {
        CAR, FIREMAN, EQUIPMENT;
    }
}

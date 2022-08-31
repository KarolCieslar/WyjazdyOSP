package pl.globoox.ospreportv3.ui.forces

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.globoox.ospreportv3.ui.forces.cars.CarFragment
import pl.globoox.ospreportv3.ui.forces.equipment.EquipmentFragment
import pl.globoox.ospreportv3.ui.forces.fireman.FiremanFragment

class ForcesViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CarFragment()
            1 -> FiremanFragment()
            else -> EquipmentFragment()
        }
    }
}

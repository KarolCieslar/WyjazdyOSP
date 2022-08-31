package pl.globoox.ospreportv3.ui.action.add

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.globoox.ospreportv3.ui.action.add.stepFirst.StepFirstFragment
import pl.globoox.ospreportv3.ui.action.add.stepSecond.StepSecondFragment
import pl.globoox.ospreportv3.ui.action.add.stepThird.StepThirdFragment
import pl.globoox.ospreportv3.ui.forces.cars.CarFragment
import pl.globoox.ospreportv3.ui.forces.equipment.EquipmentFragment
import pl.globoox.ospreportv3.ui.forces.fireman.FiremanFragment

class ActionViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StepFirstFragment()
            1 -> StepSecondFragment()
            else -> StepThirdFragment()
        }
    }
}

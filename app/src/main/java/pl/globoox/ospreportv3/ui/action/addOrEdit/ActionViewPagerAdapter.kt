package pl.globoox.ospreportv3.ui.action.addOrEdit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.ui.action.addOrEdit.stepFirst.StepFirstFragment
import pl.globoox.ospreportv3.ui.action.addOrEdit.stepSecond.StepSecondFragment
import pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird.StepThirdFragment

class ActionViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int, private val action: Action? = null) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StepFirstFragment(action)
            1 -> StepSecondFragment()
            else -> StepThirdFragment()
        }
    }
}

package pl.globoox.ospreportv3.ui.forces

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.globoox.ospreportv3.ui.forces.view.ForcesViewPagerFragment

class ForcesViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int, private var openAddDialogAtInit: Boolean? = null) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ForcesViewPagerFragment(ForcesDataType.CAR)
            1 -> ForcesViewPagerFragment(ForcesDataType.FIREMAN, openAddDialogAtInit)
            else -> ForcesViewPagerFragment(ForcesDataType.EQUIPMENT)
        }
    }
}

enum class ForcesDataType {
    CAR, FIREMAN, EQUIPMENT;
}

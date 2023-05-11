package pl.kcieslar.wyjazdyosp.ui.forces

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.kcieslar.wyjazdyosp.ui.forces.view.ForcesViewPagerFragment

class ForcesViewPagerAdapter(
    fragment: Fragment,
    private var totalCount: Int,
    private var openAddDialogAtInit: Boolean? = null
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ForcesViewPagerFragment.newInstance(ForcesDataType.CAR)
            1 -> ForcesViewPagerFragment.newInstance(ForcesDataType.FIREMAN, openAddDialogAtInit)
            else -> ForcesViewPagerFragment.newInstance(ForcesDataType.EQUIPMENT)
        }
    }
}

enum class ForcesDataType {
    CAR, FIREMAN, EQUIPMENT;
}

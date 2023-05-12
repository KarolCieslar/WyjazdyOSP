package pl.kcieslar.wyjazdyosp.ui.forces

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.forces.view.ForcesViewPagerFragment

const val FORCES_TYPE_ARG = "FORCES_TYPE_ARG"
const val OPEN_ADD_DIALOG_AT_INIT_ARG = "OPEN_ADD_DIALOG_AT_INIT_ARG"

class ForcesViewPagerAdapter(
    fragment: Fragment,
    private var totalCount: Int,
    private var openAddDialogAtInit: Boolean = false
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ForcesViewPagerFragment()
        fragment.arguments = Bundle().apply {
            when (position) {
                0 -> putSerializable(FORCES_TYPE_ARG, ForcesDataType.CAR)
                1 -> {
                    putSerializable(FORCES_TYPE_ARG, ForcesDataType.FIREMAN)
                    putBoolean(OPEN_ADD_DIALOG_AT_INIT_ARG, openAddDialogAtInit)
                }
                2 -> putSerializable(FORCES_TYPE_ARG, ForcesDataType.EQUIPMENT)
            }
        }
        return fragment
    }

}

@Parcelize
enum class ForcesDataType : Parcelable {
    CAR, FIREMAN, EQUIPMENT;
}

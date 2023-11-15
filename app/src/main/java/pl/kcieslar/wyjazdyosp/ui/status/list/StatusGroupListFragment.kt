package pl.kcieslar.wyjazdyosp.ui.status.list

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.base.BaseFragment
import pl.kcieslar.wyjazdyosp.databinding.FragmentGroupListBinding
import pl.kcieslar.wyjazdyosp.utils.setHelpDialogString
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes

@AndroidEntryPoint
class StatusGroupListFragment : BaseFragment<FragmentGroupListBinding, StatusGroupListViewModel>() {

    override val layoutId: Int = R.layout.fragment_group_list
    override val viewModel: StatusGroupListViewModel by viewModels()
    private lateinit var adapter: StatusGroupListAdapter

    override fun onReady(savedInstanceState: Bundle?) {

        adapter = StatusGroupListAdapter()
        val recyclerView = binding.statusGroupListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.groupList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }

        setHelpDialogString(HelpDialogStringRes.ACTION_LIST)
    }
}
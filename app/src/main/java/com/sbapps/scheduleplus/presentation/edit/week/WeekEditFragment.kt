package com.sbapps.scheduleplus.presentation.edit.week

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.databinding.FragmentWeekEditBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.presentation.adapters.editweek.EditWeekAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeekEditFragment : Fragment() {

    companion object {
        private const val WEEK_ID = "week_id"
        private const val UNDEFINED_WEEK_ID = -1

        fun getBundle(weekId: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(WEEK_ID, weekId)
            return bundle
        }
    }

    private var _binding: FragmentWeekEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentWeekEditBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeekEditViewModel::class.java]
    }

    private val component: FragmentComponent by lazy {
        (requireActivity().application as MyApplication).component
            .fragmentComponentFactory().create()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    private lateinit var adapter: EditWeekAdapter
    private var weekId = UNDEFINED_WEEK_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            weekId = it.getInt(WEEK_ID)
        }
        setObservable()
        binding.cardViewButtonAddScheduleItem.setOnClickListener {
            val exampleName = ContextCompat.getString(requireContext(), R.string.example)
            val examplePlace = ContextCompat.getString(requireContext(), R.string.place)
            viewModel.setScheduleEditDialogState(ScheduleItem(name = exampleName, place = examplePlace, weekId = weekId))
        }
    }

    private fun setObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is WeekEditFragmentState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            binding.nestedScrollView.visibility = View.VISIBLE
                            setupAdapter(state.currencyScheduleItemList.filter { it.weekId == weekId })
                        }

                        is WeekEditFragmentState.Error -> {
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }

                        is WeekEditFragmentState.ScheduleItemEditDialog -> {
                            showDialog(state.scheduleItem)
                        }

                        WeekEditFragmentState.Loading -> {
                            binding.nestedScrollView.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.scheduleItemList.collect {
                    viewModel.setContentState(it)
                }
            }
        }
    }

    private fun setupAdapter(scheduleItemList: List<ScheduleItem>) {
        adapter = EditWeekAdapter(requireContext(), scheduleItemList)
        adapter.onScheduleItemClickListener = { scheduleItem ->
            viewModel.setScheduleEditDialogState(scheduleItem)
        }
        adapter.onScheduleItemSwiped = {
            viewModel.deleteScheduleItem(it)
        }
        binding.recyclerViewWeekEdit.adapter = adapter
    }

    private fun showDialog(scheduleItem: ScheduleItem) {
        val scheduleEditDialog =
            ScheduleItemEditDialog(requireContext(), scheduleItem) {
                if (it.id == ScheduleItem.UNDEFINED_ID) {
                    viewModel.addScheduleItem(it)
                } else {
                    viewModel.editScheduleItem(it)
                }
            }
        scheduleEditDialog.setCanceledOnTouchOutside(false)
        scheduleEditDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
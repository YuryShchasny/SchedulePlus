package com.sbapps.scheduleplus.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.databinding.FragmentScheduleMainBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.dayofweek.DayOfWeekListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleMainFragment : Fragment() {

    private var _binding: FragmentScheduleMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleMainBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ScheduleMainViewModel::class.java]
    }

    private val component : FragmentComponent by lazy {
        (requireActivity().application as MyApplication).component
            .fragmentComponentFactory().create()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {state ->
                    when(state) {
                        is ScheduleMainState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerViewMainSchedule.visibility = View.VISIBLE
                            updateAdapter(state.currencyWeekList, state.currencyScheduleItemList)
                        }
                        is ScheduleMainState.Error -> {
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }
                        ScheduleMainState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerViewMainSchedule.visibility = View.GONE
                        }
                    }
                }
            }
        }
        viewModel.scheduleItemList.observe(viewLifecycleOwner) {scheduleItemList ->
            viewModel.setScheduleItemList(scheduleItemList)
        }
        viewModel.weekList.observe(viewLifecycleOwner) {weekList ->
            viewModel.setWeekList(weekList)
        }

    }
    private fun updateAdapter(weekList: List<Week>, scheduleItemList: List<ScheduleItem>) {
        val adapter = DayOfWeekListAdapter(requireContext(), weekList, scheduleItemList)
        binding.recyclerViewMainSchedule.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
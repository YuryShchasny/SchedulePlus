package com.sbapps.scheduleplus.presentation.schedule

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
import com.sbapps.scheduleplus.databinding.FragmentScheduleBinding
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.schedule.ScheduleAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ScheduleViewModel::class.java]
    }

    private val component by lazy {
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
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ScheduleFragmentState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerViewSchedule.visibility = View.VISIBLE
                            updateAdapter(state.currencyWeekList, state.currencyScheduleItemList)
                        }

                        is ScheduleFragmentState.Error -> {
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }

                        ScheduleFragmentState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerViewSchedule.visibility = View.GONE
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.scheduleItemList.collect {
                    viewModel.setScheduleItemList(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.weekList.collect {
                    viewModel.setWeekList(it)
                }
            }
        }
    }

    private fun updateAdapter(weekList: List<Week>, scheduleItemList: List<ScheduleItem>) {
        val adapter = ScheduleAdapter(requireContext(), weekList, scheduleItemList)
        binding.recyclerViewSchedule.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
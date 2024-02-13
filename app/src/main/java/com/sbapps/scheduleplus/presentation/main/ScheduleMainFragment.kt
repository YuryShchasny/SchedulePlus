package com.sbapps.scheduleplus.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.databinding.FragmentScheduleMainBinding
import com.sbapps.scheduleplus.presentation.adapters.dayofweek.DayOfWeekListAdapter

class ScheduleMainFragment : Fragment() {

    private var _binding: FragmentScheduleMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleMainBinding is null")

    private val viewModel: ScheduleMainViewModel by lazy {
        ViewModelProvider(this)[ScheduleMainViewModel::class.java]
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
        viewModel.isLoadFinished.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewMainSchedule.visibility = View.VISIBLE
            }
        }
        viewModel.weekList.observe(viewLifecycleOwner) {
            viewModel.setIsLoadFinished(true)
            val adapter = DayOfWeekListAdapter(requireContext(), it)
            binding.recyclerViewMainSchedule.adapter = adapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
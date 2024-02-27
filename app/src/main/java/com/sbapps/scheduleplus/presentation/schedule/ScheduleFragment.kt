package com.sbapps.scheduleplus.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.databinding.FragmentScheduleBinding
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.schedule.ScheduleAdapter

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleBinding is null")

    private val viewModel: ScheduleViewModel by lazy {
        ViewModelProvider(this)[ScheduleViewModel::class.java]
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
        viewModel.isLoad.observe(viewLifecycleOwner) {
            if (!it) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewSchedule.visibility = View.VISIBLE
            }
        }
        viewModel.scheduleItemList.observe(viewLifecycleOwner) {
            viewModel.setIsLoad(false)
            updateAdapter(viewModel.getWeekList(), it)
        }
        viewModel.weekList.observe(viewLifecycleOwner) {
            viewModel.setIsLoad(false)
            updateAdapter(it, viewModel.getScheduleItemList())
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
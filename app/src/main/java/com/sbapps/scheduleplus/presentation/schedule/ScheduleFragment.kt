package com.sbapps.scheduleplus.presentation.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.databinding.FragmentScheduleBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.schedule.ScheduleAdapter
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
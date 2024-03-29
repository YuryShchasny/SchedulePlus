package com.sbapps.scheduleplus.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.databinding.FragmentScheduleMainBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.presentation.adapters.dayofweek.DayOfWeekListAdapter
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
        viewModel.isLoad.observe(viewLifecycleOwner) {
            if (!it) {
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewMainSchedule.visibility = View.VISIBLE
            }
        }
        viewModel.scheduleItemList.observe(viewLifecycleOwner){
            viewModel.setIsLoad(false)
            updateAdapter(viewModel.getWeekList(), it)
        }
        viewModel.weekList.observe(viewLifecycleOwner) {
            viewModel.setIsLoad(false)
            updateAdapter(it, viewModel.getScheduleItemList())
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
package com.sbapps.scheduleplus.presentation.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.databinding.FragmentScheduleEditBinding
import com.sbapps.scheduleplus.presentation.adapters.week.WeekListAdapter
import com.sbapps.scheduleplus.presentation.edit.week.WeekEditFragment

class ScheduleEditFragment : Fragment() {

    private var _binding: FragmentScheduleEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScheduleEditBinding is null")

    private val viewModel: ScheduleEditViewModel by lazy {
        ViewModelProvider(this)[ScheduleEditViewModel::class.java]
    }
    private lateinit var adapter: WeekListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weekList.observe(viewLifecycleOwner) {
            setupRecyclerView()
            adapter.submitList(it)
        }
        binding.cardViewButtonAdd.setOnClickListener {
            viewModel.addWeek()
        }
    }

    private fun setupRecyclerView() {
        adapter = WeekListAdapter(requireContext())
        binding.recyclerViewWeekEdit.adapter = adapter
        binding.recyclerViewWeekEdit.itemAnimator = null
        setupLongClickListener()
        setupOnClickListener()
        setupSwipeListener()
    }

    private fun setupSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteWeek(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewWeekEdit)
    }

    private fun setupOnClickListener() {
        adapter.onWeekClickListener = {
            val bundle = WeekEditFragment.getBundle(it.id)
            findNavController().navigate(R.id.action_open_week_edit, bundle)
        }
    }

    private fun setupLongClickListener() {
        adapter.onWeekLongClickListener = {
            viewModel.setWeekActive(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
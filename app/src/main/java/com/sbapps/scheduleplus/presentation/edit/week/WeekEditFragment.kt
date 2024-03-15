package com.sbapps.scheduleplus.presentation.edit.week

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.databinding.FragmentWeekEditBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.di.ViewModelFactory
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.presentation.adapters.editweek.EditWeekAdapter
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

    private val component : FragmentComponent by lazy {
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
        viewModel.setDialogStateClosed()
        setObservable()
        binding.cardViewButtonAddScheduleItem.setOnClickListener {
            if (viewModel.getDialogState()) {
                val exampleName = ContextCompat.getString(requireContext(), R.string.example)
                val examplePlace = ContextCompat.getString(requireContext(), R.string.place)
                showDialog(ScheduleItem(name = exampleName, place = examplePlace, weekId = weekId))
            }
        }
    }

    private fun setObservable() {
        viewModel.dialogClosed.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.setIsLoad(true)
            }
        }
        viewModel.isLoad.observe(viewLifecycleOwner) {
            if (!it) {
                binding.progressBar.visibility = View.GONE
                binding.nestedScrollView.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
        viewModel.scheduleItemList.observe(viewLifecycleOwner) { scheduleItemList ->
            viewModel.setIsLoad(false)
            setupAdapter(scheduleItemList.filter { it.weekId == weekId })
        }
    }

    private fun setupAdapter(scheduleItemList: List<ScheduleItem>) {
        adapter = EditWeekAdapter(requireContext(), scheduleItemList)
        adapter.onScheduleItemClickListener = { scheduleItem ->
            if (viewModel.getDialogState()) {
                showDialog(scheduleItem)
            }
        }
        adapter.onScheduleItemSwiped = {
            viewModel.setIsLoad(false)
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
                viewModel.setDialogStateClosed()
            }
        scheduleEditDialog.setCanceledOnTouchOutside(false)
        scheduleEditDialog.show()
        viewModel.setDialogStateOpened()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
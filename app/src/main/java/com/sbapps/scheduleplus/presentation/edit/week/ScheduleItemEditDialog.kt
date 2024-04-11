package com.sbapps.scheduleplus.presentation.edit.week

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.SVBar
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.entity.DayOfWeek
import com.sbapps.scheduleplus.domain.entity.ScheduleItem

class ScheduleItemEditDialog(
    context: Context,
    private val scheduleItem: ScheduleItem,
    private val onSaveClickListener: (ScheduleItem) -> Unit
) : Dialog(context) {

    private lateinit var textViewStartTime: TextView
    private lateinit var textViewEndTime: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewPlace: TextView
    private lateinit var viewColorShape: View
    private lateinit var hourPickerStartTime: NumberPicker
    private lateinit var minutePickerStartTime: NumberPicker
    private lateinit var hourPickerEndTime: NumberPicker
    private lateinit var minutePickerEndTime: NumberPicker
    private lateinit var editTextName: EditText
    private lateinit var editTextPlace: EditText
    private lateinit var spinnerDayOfWeek: Spinner
    private lateinit var buttonSave: Button
    private lateinit var colorPicker: ColorPicker
    private lateinit var svBar: SVBar

    companion object {
        private const val MAX_HOUR_HUMBER = 23
        private const val MAX_MINUTE_HUMBER = 59
        private const val MIN_TIME_HUMBER = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_schedule_item_edit)
        window?.setBackgroundDrawableResource(R.color.transparent)
        initViews()
        setViewsOptions()
        setScheduleOptions()
        setListeners()
    }

    private fun initViews() {
        textViewStartTime = findViewById(R.id.textViewStartTime)
        textViewEndTime = findViewById(R.id.textViewEndTime)
        textViewName = findViewById(R.id.textViewName)
        textViewPlace = findViewById(R.id.textViewPlace)
        viewColorShape = findViewById(R.id.viewColorShape)
        hourPickerStartTime = findViewById(R.id.hourPickerStartTime)
        minutePickerStartTime = findViewById(R.id.minutePickerStartTime)
        hourPickerEndTime = findViewById(R.id.hourPickerEndTime)
        minutePickerEndTime = findViewById(R.id.minutePickerEndTime)
        editTextName = findViewById(R.id.editTextName)
        editTextPlace = findViewById(R.id.editTextPlace)
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek)
        buttonSave = findViewById(R.id.buttonSave)
        colorPicker = findViewById(R.id.color_picker)
        svBar = findViewById(R.id.svbar)
    }

    private fun setViewsOptions() {
        colorPicker.addSVBar(svBar)
        colorPicker.showOldCenterColor = false
        hourPickerStartTime.maxValue = MAX_HOUR_HUMBER
        hourPickerEndTime.maxValue = MAX_HOUR_HUMBER
        hourPickerStartTime.minValue = MIN_TIME_HUMBER
        hourPickerEndTime.minValue = MIN_TIME_HUMBER
        minutePickerStartTime.maxValue = MAX_MINUTE_HUMBER
        minutePickerEndTime.maxValue = MAX_MINUTE_HUMBER
        minutePickerStartTime.minValue = MIN_TIME_HUMBER
        minutePickerEndTime.minValue = MIN_TIME_HUMBER
        setSpinnerAdapter()
    }

    private fun setScheduleOptions() {
        textViewStartTime.text = scheduleItem.startTime
        textViewEndTime.text = scheduleItem.endTime
        hourPickerStartTime.value = getHours(scheduleItem.startTime)
        minutePickerStartTime.value = getMinutes(scheduleItem.startTime)
        hourPickerEndTime.value = getHours(scheduleItem.endTime)
        minutePickerEndTime.value = getMinutes(scheduleItem.endTime)
        textViewName.text = scheduleItem.name
        editTextName.setText(scheduleItem.name)
        textViewPlace.text = scheduleItem.place
        editTextPlace.setText(scheduleItem.place)
        viewColorShape.backgroundTintList = ColorStateList.valueOf(scheduleItem.color)
        spinnerDayOfWeek.setSelection(scheduleItem.dayOfWeek.ordinal)
        colorPicker.color = scheduleItem.color
        colorPicker.oldCenterColor = scheduleItem.color
    }

    private fun setListeners() {
        colorPicker.setOnColorChangedListener {
            viewColorShape.backgroundTintList = ColorStateList.valueOf(it)
        }
        editTextName.addTextChangedListener {
            textViewName.text = it.toString()
        }
        editTextPlace.addTextChangedListener {
            textViewPlace.text = it.toString()
        }
        hourPickerStartTime.setOnValueChangedListener { _, _, newVal ->
            setTimeView(textViewStartTime, newVal, minutePickerStartTime.value)
        }
        minutePickerStartTime.setOnValueChangedListener { _, _, newVal ->
            setTimeView(textViewStartTime, hourPickerStartTime.value, newVal)
        }
        hourPickerEndTime.setOnValueChangedListener { _, _, newVal ->
            setTimeView(textViewEndTime, newVal, minutePickerEndTime.value)
        }
        minutePickerEndTime.setOnValueChangedListener { _, _, newVal ->
            setTimeView(textViewEndTime, hourPickerEndTime.value, newVal)
        }
        buttonSave.setOnClickListener {
            onSaveClickListener.invoke(getEditSchedule())
            this.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeView(timeView: TextView, hour: Int, minute: Int) {
        timeView.text = String.format("%02d:%02d", hour, minute)
    }

    private fun getHours(time: String): Int = time.substring(0, 2).toInt()
    private fun getMinutes(time: String): Int = time.substring(3, 5).toInt()

    private fun setSpinnerAdapter() {
        val adapter = ArrayAdapter(
            context,
            R.layout.spinner_day_of_week_item,
            context.resources.getStringArray(R.array.day_of_week)
        )
        spinnerDayOfWeek.adapter = adapter
    }

    private fun getEditSchedule(): ScheduleItem {
        val startTime = textViewStartTime.text.toString()
        val endTime = textViewEndTime.text.toString()
        val name = textViewName.text.toString().trim()
        val place = textViewPlace.text.toString().trim()
        val dayOfWeek = DayOfWeek.entries[spinnerDayOfWeek.selectedItemPosition]
        val color = viewColorShape.backgroundTintList?.defaultColor
            ?: throw RuntimeException("Failed to get color when saving ScheduleItem")
        return scheduleItem.copy(
            name = name,
            startTime = startTime,
            endTime = endTime,
            dayOfWeek = dayOfWeek,
            place = place,
            color = color
        )
    }

    override fun onBackPressed() {
        onSaveClickListener.invoke(getEditSchedule())
        this.dismiss()
    }

}
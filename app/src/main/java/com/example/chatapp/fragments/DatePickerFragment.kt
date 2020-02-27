package com.example.chatapp.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment(ProfileDateView: TextView) : DialogFragment(), DatePickerDialog.OnDateSetListener{
    private var ProfileDateView = ProfileDateView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val monthPlusOne = month + 1
        val pickedDate:String = "$dayOfMonth/$monthPlusOne/$year"

        ProfileDateView.text = pickedDate
    }
}

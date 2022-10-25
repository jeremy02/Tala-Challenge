package com.example.myapplication.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.showSnack(message: String, action: String = "", actionListener: () -> Unit = {}): Snackbar {
    var snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    if (action != "") {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.setAction(action) {
            actionListener()
            snackbar.dismiss()
        }
    }
    snackbar.show()
    return snackbar
}

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun EditText.dismissKeyboard() {
    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun formatDate(dateDue: Long): String {
    val dateDueAsDate: Date = Date(dateDue)
    return getTimeAgo(dateDueAsDate)
}

fun getTimeAgo(dateDue: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = dateDue

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val currentCalendar = Calendar.getInstance()

    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentCalendar.get(Calendar.MINUTE)

    val futureSuffix = "due in "
    val pastSuffix = "due "

    return if (currentYear < year ) {  // This will calculate days in the future
        val interval = year - currentYear
        if (interval == 1) "$futureSuffix $interval year" else "$futureSuffix $interval years"
    } else if (currentMonth < month) {
        val interval = month - currentMonth
        if (interval == 1) "$futureSuffix $interval month" else "$futureSuffix $interval months"
    } else  if (currentDay < day) {
        val interval = day - currentDay
        if (interval == 1) "due tomorrow" else "$futureSuffix $interval days"
    } else if (currentHour < hour) {
        val interval = hour - currentHour
        if (interval == 1) "$futureSuffix $interval hour" else "$futureSuffix $interval hours"
    } else if (currentMinute < minute) {
        val interval = minute - currentMinute
        if (interval == 1) "$futureSuffix $interval minute" else "$futureSuffix $interval minutes"
    } else if (year < currentYear ) {  // This will calculate days in the past
        val interval = currentYear - year
        if (interval == 1) "$pastSuffix $interval year ago" else "$pastSuffix $interval years ago"
    } else if (month < currentMonth) {
        val interval = currentMonth - month
        if (interval == 1) "$pastSuffix $interval month ago" else "$pastSuffix $interval months ago"
    } else  if (day < currentDay) {
        val interval = currentDay - day
        if (interval == 1) "due yesterday" else "$pastSuffix $interval days ago"
    } else if (hour < currentHour) {
        val interval = currentHour - hour
        if (interval == 1) "$pastSuffix $interval hour ago" else "$pastSuffix $interval hours ago"
    } else if (minute < currentMinute) {
        val interval = currentMinute - minute
        if (interval == 1) "$pastSuffix $interval minute ago" else "$pastSuffix $interval minutes ago"
    } else {
        "a moment ago"
    }
}



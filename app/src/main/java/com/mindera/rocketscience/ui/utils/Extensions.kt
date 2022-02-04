package com.mindera.rocketscience.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.internal.UTC
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@SuppressLint("SimpleDateFormat")
fun Long.convertToDateAndTime(): Pair<String, String> {
    val formatterDate = SimpleDateFormat("dd/MM/yyyy")
    formatterDate.timeZone = TimeZone.getDefault()
    val formatterTime = SimpleDateFormat("HH:mm")
    formatterTime.timeZone = TimeZone.getDefault()
    val netDate = Date(this * 1000)
    return formatterDate.format(netDate) to formatterTime.format(netDate)
}

fun Long.daysFromSince(): Long {
    val netDate = Date(this * 1000)
    val actualDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
    val diff = actualDate - netDate.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}
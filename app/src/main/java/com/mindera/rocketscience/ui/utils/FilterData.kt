package com.mindera.rocketscience.ui.utils

import com.google.gson.Gson
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort

data class FilterData(
    val sorting: Sort = Sort.ASC,
    val launchSuccessFilter: LaunchSuccessFilter = LaunchSuccessFilter.ALL,
    val filterByDate: Boolean = false,
    val minYear: Int = 0,
    val maxYear: Int = 0
)

fun FilterData.toJson(): String{
    return Gson().toJson(this)
}

fun String.fromJson(): FilterData{
    return Gson().fromJson(this, FilterData::class.java)
}
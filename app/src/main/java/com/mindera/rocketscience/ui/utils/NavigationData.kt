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

data class LinksData(
    val articleLink: String = "",
    val wikipediaLink: String = "",
    val videoLink: String = "",
)


fun<T> T.toJson(): String{
    return Gson().toJson(this)
}

inline fun<reified T> String.fromJson(): T{
    return Gson().fromJson(this, T::class.java)
}
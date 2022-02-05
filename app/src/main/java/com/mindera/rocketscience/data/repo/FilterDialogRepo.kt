package com.mindera.rocketscience.data.repo

import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.local.entity.LaunchYearMaxMin
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class FilterDialogRepo @Inject constructor(private val localSource: SpaceXDb) {
    fun letMaxMinLaunch(): Flow<LaunchYearMaxMin> = flow {
        val maxMinYear = localSource.launchesDao().getMaxMinLaunchYear()
        emit(maxMinYear)
    }
}
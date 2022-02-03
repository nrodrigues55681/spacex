package com.mindera.rocketscience.data

import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.Launches

fun CompanyInfoDto.toDomain(): CompanyInfo = CompanyInfo(
    name = name ?: "", founder = founder ?: "", employees = employees ?: 0,
    founded = founded ?: 0, launchSites = launchSites ?: 0, valuation = valuation ?: 0)

fun LaunchesDto.toDomain(): Launches = Launches(
    missionName = missionName ?: "",
    rocketName = rocket?.rocketName ?: "",
    rocketType = rocket?.rocketType ?: "",
    launchDateUnix = launchDateUnix ?: 0,
    launchSuccess = launchSuccess ?: false,
    missionPatchSmall = links?.missionPatchSmall ?: "")
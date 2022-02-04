package com.mindera.rocketscience.data

import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.Launches

fun CompanyInfoDto.toDomain(): CompanyInfo = CompanyInfo(
    name = name ?: "",
    founder = founder ?: "",
    employees = employees ?: 0,
    founded = founded ?: 0,
    launchSites = launchSites ?: 0,
    valuation = valuation ?: 0)


fun CompanyInfoDto.toEntity(): CompanyInfoEntity = CompanyInfoEntity(
    name = name ?: "",
    founder = founder ?: "",
    employees = employees ?: 0,
    founded = founded ?: 0,
    launchSites = launchSites ?: 0,
    valuation = valuation ?: 0)

fun CompanyInfoEntity.toDomain(): CompanyInfo = CompanyInfo(
    name = name,
    founder = founder,
    founded = founded,
    employees = employees,
    launchSites = launchSites,
    valuation = valuation)

fun LaunchesDto.toDomain(): Launches = Launches(
    flightNumber = flightNumber,
    missionName = missionName ?: "",
    rocketName = rocket?.rocketName ?: "",
    rocketType = rocket?.rocketType ?: "",
    launchDateUnix = launchDateUnix ?: 0,
    launchSuccess = launchSuccess ?: false,
    missionPatchSmall = links?.missionPatchSmall ?: "")

fun LaunchesDto.toEntity(): LaunchesEntity = LaunchesEntity(
    flightNumber = flightNumber,
    missionName = missionName ?: "",
    rocketName = rocket?.rocketName ?: "",
    rocketType = rocket?.rocketType ?: "",
    launchDateUnix = launchDateUnix ?: 0,
    launchSuccess = launchSuccess ?: false,
    missionPatchSmall = links?.missionPatchSmall ?: "")

fun LaunchesEntity.toDomain(): Launches = Launches(
    flightNumber = flightNumber,
    missionName = missionName,
    rocketName = rocketName,
    rocketType = rocketType,
    launchDateUnix = launchDateUnix,
    launchSuccess = launchSuccess,
    missionPatchSmall = missionPatchSmall)

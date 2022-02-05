package com.mindera.rocketscience.data

import android.annotation.SuppressLint
import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.Launches
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

fun LaunchesDto.toEntity(): LaunchesEntity = LaunchesEntity(
    flightNumber = flightNumber,
    missionName = missionName ?: "",
    rocketName = rocket?.rocketName ?: "",
    rocketType = rocket?.rocketType ?: "",
    launchDateUnix = launchDateUnix ?: 0,
    launchSuccess = launchSuccess ?: false,
    launchYear = launchYear ?: 0,
    missionPatchSmall = links?.missionPatchSmall ?: "",
    articleLink = links?.articleLink ?: "",
    wikipedia = links?.wikipedia ?: "",
    videoLink = links?.videoLink ?: "")

fun LaunchesEntity.toDomain(): Launches = Launches(
    flightNumber = flightNumber,
    missionName = missionName,
    rocketName = rocketName,
    rocketType = rocketType,
    launchDateUnix = launchDateUnix,
    launchSuccess = launchSuccess,
    missionPatchSmall = missionPatchSmall,
    articleLink = articleLink,
    wikipedia = wikipedia,
    videoLink = videoLink)

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
package com.mindera.rocketscience.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class LaunchesEntity(
    @PrimaryKey val flightNumber : Int,
    val missionName: String,
    val rocketName: String,
    val rocketType: String,
    val launchDateUnix: Long,
    val launchYear: Int,
    val launchSuccess: Boolean,
    val missionPatchSmall: String,
    val articleLink: String,
    val wikipedia: String,
    val videoLink: String,
)

data class LaunchYearMaxMin(
    @ColumnInfo(name = "launch_year_max") val launchYearMax: Int,
    @ColumnInfo(name = "launch_year_min") val launchYearMin: Int
)

@Entity(tableName = "company_info")
data class CompanyInfoEntity(
    @PrimaryKey val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launchSites: Int,
    val valuation: Long
)
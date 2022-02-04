package com.mindera.rocketscience.domain

data class Launches (
    val flightNumber: Int,
    val missionName: String,
    val rocketName: String,
    val rocketType: String,
    val launchDateUnix: Long,
    val launchSuccess: Boolean,
    val missionPatchSmall: String)

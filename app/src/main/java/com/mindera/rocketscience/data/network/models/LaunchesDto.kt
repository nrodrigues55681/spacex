package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class LaunchesDto(
    @SerializedName("flight_number") val flightNumber: Int,
    @SerializedName("mission_name") val missionName: String? = null,
    @SerializedName("launch_date_unix") val launchDateUnix: Long? = null,
    @SerializedName("rocket") val rocket: Rocket? = Rocket(),
    @SerializedName("launch_success") val launchSuccess: Boolean? = null,
    @SerializedName("links") val links: LaunchesLinks? = LaunchesLinks(),
)

data class Rocket(
    @SerializedName("rocket_name") val rocketName: String? = null,
    @SerializedName("rocket_type") val rocketType: String? = null
)

data class LaunchesLinks(
    @SerializedName("mission_patch_small") val missionPatchSmall: String? = null
)
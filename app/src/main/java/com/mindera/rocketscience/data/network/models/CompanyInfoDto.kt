package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @SerializedName("name") val name: String? = null,
    @SerializedName("founder") val founder: String? = null,
    @SerializedName("founded") val founded: Int? = null,
    @SerializedName("employees") val employees: Int? = null,
    @SerializedName("launch_sites") val launchSites: Int? = null,
    @SerializedName("valuation") val valuation: Long? = null
)

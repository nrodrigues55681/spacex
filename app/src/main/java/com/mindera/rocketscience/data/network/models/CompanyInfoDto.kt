package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @SerializedName("name") val name: String? = null,
    @SerializedName("founder") val founder: String? = null,
    @SerializedName("founded") val founded: Int? = null,
    @SerializedName("employees") val employees: Int? = null,
    @SerializedName("vehicles") val vehicles: Int? = null,
    @SerializedName("launch_sites") val launchSites: Int? = null,
    @SerializedName("test_sites") val testSites: Int? = null,
    @SerializedName("ceo") val ceo: String? = null,
    @SerializedName("cto") val cto: String? = null,
    @SerializedName("coo") val coo: String? = null,
    @SerializedName("cto_propulsion") val ctoPropulsion: String? = null,
    @SerializedName("valuation") val valuation: Long? = null,
    @SerializedName("headquarters") val headquarters: Headquarters? = Headquarters(),
    @SerializedName("links") val links: Links? = Links(),
    @SerializedName("summary") val summary: String? = null
)

data class Headquarters(
    @SerializedName("address") val address: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null
)

data class Links(
    @SerializedName("website") val website: String? = null,
    @SerializedName("flickr") val flickr: String? = null,
    @SerializedName("twitter") val twitter: String? = null,
    @SerializedName("elon_twitter") val elonTwitter: String? = null
)
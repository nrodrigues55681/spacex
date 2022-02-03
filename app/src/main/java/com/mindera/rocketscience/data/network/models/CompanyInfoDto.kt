package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @SerializedName("name") var name: String? = null,
    @SerializedName("founder") var founder: String? = null,
    @SerializedName("founded") var founded: Int? = null,
    @SerializedName("employees") var employees: Int? = null,
    @SerializedName("vehicles") var vehicles: Int? = null,
    @SerializedName("launch_sites") var launchSites: Int? = null,
    @SerializedName("test_sites") var testSites: Int? = null,
    @SerializedName("ceo") var ceo: String? = null,
    @SerializedName("cto") var cto: String? = null,
    @SerializedName("coo") var coo: String? = null,
    @SerializedName("cto_propulsion") var ctoPropulsion: String? = null,
    @SerializedName("valuation") var valuation: Int? = null,
    @SerializedName("headquarters") var headquarters: Headquarters? = Headquarters(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("summary") var summary: String? = null
)

data class Headquarters(
    @SerializedName("address") var address: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null
)

data class Links(
    @SerializedName("website") var website: String? = null,
    @SerializedName("flickr") var flickr: String? = null,
    @SerializedName("twitter") var twitter: String? = null,
    @SerializedName("elon_twitter") var elonTwitter: String? = null
)
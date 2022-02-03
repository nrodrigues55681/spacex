package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class LaunchesDto(
    @SerializedName("flight_number") val flightNumber: Int? = null,
    @SerializedName("mission_name") val missionName: String? = null,
    @SerializedName("mission_id") val missionId: ArrayList<String> = arrayListOf(),
    @SerializedName("upcoming") val upcoming: Boolean? = null,
    @SerializedName("launch_year") val launchYear: String? = null,
    @SerializedName("launch_date_unix") val launchDateUnix: Int? = null,
    @SerializedName("launch_date_utc") val launchDateUtc: String? = null,
    @SerializedName("launch_date_local") val launchDateLocal: String? = null,
    @SerializedName("is_tentative") val isTentative: Boolean? = null,
    @SerializedName("tentative_max_precision") val tentativeMaxPrecision: String? = null,
    @SerializedName("tbd") val tbd: Boolean? = null,
    @SerializedName("launch_window") val launchWindow: Int? = null,
    @SerializedName("rocket") val rocket: Rocket? = Rocket(),
    @SerializedName("ships") val ships: ArrayList<String> = arrayListOf(),
    @SerializedName("telemetry") val telemetry: Telemetry? = Telemetry(),
    @SerializedName("launch_site") val launchSite: LaunchSite? = LaunchSite(),
    @SerializedName("launch_success") val launchSuccess: Boolean? = null,
    @SerializedName("launch_failure_details") val launchFailureDetails: LaunchFailureDetails? = LaunchFailureDetails(),
    @SerializedName("links") val links: LaunchesLinks? = LaunchesLinks(),
    @SerializedName("details") val details: String? = null,
    @SerializedName("static_fire_date_utc") val staticFireDateUtc: String? = null,
    @SerializedName("static_fire_date_unix") val staticFireDateUnix: Int? = null,
    @SerializedName("timeline") val timeline: Timeline? = Timeline(),
    @SerializedName("crew") val crew: String? = null
)

data class Cores(
    @SerializedName("core_serial") val coreSerial: String? = null,
    @SerializedName("flight") val flight: Int? = null,
    @SerializedName("block") val block: String? = null,
    @SerializedName("gridfins") val gridfins: Boolean? = null,
    @SerializedName("legs") val legs: Boolean? = null,
    @SerializedName("reused") val reused: Boolean? = null,
    @SerializedName("land_success") val landSuccess: String? = null,
    @SerializedName("landing_intent") val landingIntent: Boolean? = null,
    @SerializedName("landing_type") val landingType: String? = null,
    @SerializedName("landing_vehicle") val landingVehicle: String? = null
)

data class FirstStage(
    @SerializedName("cores") val cores: ArrayList<Cores> = arrayListOf()
)

data class OrbitParams(
    @SerializedName("reference_system") val referenceSystem: String? = null,
    @SerializedName("regime") val regime: String? = null,
    @SerializedName("longitude") val longitude: String? = null,
    @SerializedName("semi_major_axis_km") val semiMajorAxisKm: String? = null,
    @SerializedName("eccentricity") val eccentricity: String? = null,
    @SerializedName("periapsis_km") val periapsisKm: Int? = null,
    @SerializedName("apoapsis_km") val apoapsisKm: Int? = null,
    @SerializedName("inclination_deg") val inclinationDeg: Int? = null,
    @SerializedName("period_min") val periodMin: String? = null,
    @SerializedName("lifespan_years") val lifespanYears: String? = null,
    @SerializedName("epoch") val epoch: String? = null,
    @SerializedName("mean_motion") val meanMotion: String? = null,
    @SerializedName("raan") val raan: String? = null,
    @SerializedName("arg_of_pericenter") val argOfPericenter: String? = null,
    @SerializedName("mean_anomaly") val meanAnomaly: String? = null
)

data class Payloads(
    @SerializedName("payload_id") val payloadId: String? = null,
    @SerializedName("norad_id") val noradId: ArrayList<String> = arrayListOf(),
    @SerializedName("reused") val reused: Boolean? = null,
    @SerializedName("customers") val customers: ArrayList<String> = arrayListOf(),
    @SerializedName("nationality") val nationality: String? = null,
    @SerializedName("manufacturer") val manufacturer: String? = null,
    @SerializedName("payload_type") val payloadType: String? = null,
    @SerializedName("payload_mass_kg") val payloadMassKg: Int? = null,
    @SerializedName("payload_mass_lbs") val payloadMassLbs: Int? = null,
    @SerializedName("orbit") val orbit: String? = null,
    @SerializedName("orbit_params") val orbitParams: OrbitParams? = OrbitParams()
)

data class SecondStage(
    @SerializedName("block") val block: Int? = null,
    @SerializedName("payloads") val payloads: ArrayList<Payloads> = arrayListOf()
)

data class Fairings(
    @SerializedName("reused") val reused: Boolean? = null,
    @SerializedName("recovery_attempt") val recoveryAttempt: Boolean? = null,
    @SerializedName("recovered") val recovered: Boolean? = null,
    @SerializedName("ship") val ship: String? = null
)

data class Rocket(
    @SerializedName("rocket_id") val rocketId: String? = null,
    @SerializedName("rocket_name") val rocketName: String? = null,
    @SerializedName("rocket_type") val rocketType: String? = null,
    @SerializedName("first_stage") val firstStage: FirstStage? = FirstStage(),
    @SerializedName("second_stage") val secondStage: SecondStage? = SecondStage(),
    @SerializedName("fairings") val fairings: Fairings? = Fairings()
)

data class Telemetry(
    @SerializedName("flight_club") val flightClub: String? = null
)

data class LaunchSite(
    @SerializedName("site_id") val siteId: String? = null,
    @SerializedName("site_name") val siteName: String? = null,
    @SerializedName("site_name_long") val siteNameLong: String? = null
)

data class LaunchFailureDetails(
    @SerializedName("time") val time: Int? = null,
    @SerializedName("altitude") val altitude: String? = null,
    @SerializedName("reason") val reason: String? = null
)

data class LaunchesLinks(
    @SerializedName("mission_patch") val missionPatch: String? = null,
    @SerializedName("mission_patch_small") val missionPatchSmall: String? = null,
    @SerializedName("reddit_campaign") val redditCampaign: String? = null,
    @SerializedName("reddit_launch") val redditLaunch: String? = null,
    @SerializedName("reddit_recovery") val redditRecovery: String? = null,
    @SerializedName("reddit_media") val redditMedia: String? = null,
    @SerializedName("presskit") val presskit: String? = null,
    @SerializedName("article_link") val articleLink: String? = null,
    @SerializedName("wikipedia") val wikipedia: String? = null,
    @SerializedName("video_link") val videoLink: String? = null,
    @SerializedName("youtube_id") val youtubeId: String? = null,
    @SerializedName("flickr_images") val flickrImages: ArrayList<String> = arrayListOf()
)

data class Timeline(
    @SerializedName("webcast_liftoff") val webcastLiftoff: Int? = null
)
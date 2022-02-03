package com.mindera.rocketscience.data.network.models

import com.google.gson.annotations.SerializedName

data class LaunchesDto(
    @SerializedName("flight_number") var flightNumber: Int? = null,
    @SerializedName("mission_name") var missionName: String? = null,
    @SerializedName("mission_id") var missionId: ArrayList<String> = arrayListOf(),
    @SerializedName("upcoming") var upcoming: Boolean? = null,
    @SerializedName("launch_year") var launchYear: String? = null,
    @SerializedName("launch_date_unix") var launchDateUnix: Int? = null,
    @SerializedName("launch_date_utc") var launchDateUtc: String? = null,
    @SerializedName("launch_date_local") var launchDateLocal: String? = null,
    @SerializedName("is_tentative") var isTentative: Boolean? = null,
    @SerializedName("tentative_max_precision") var tentativeMaxPrecision: String? = null,
    @SerializedName("tbd") var tbd: Boolean? = null,
    @SerializedName("launch_window") var launchWindow: Int? = null,
    @SerializedName("rocket") var rocket: Rocket? = Rocket(),
    @SerializedName("ships") var ships: ArrayList<String> = arrayListOf(),
    @SerializedName("telemetry") var telemetry: Telemetry? = Telemetry(),
    @SerializedName("launch_site") var launchSite: LaunchSite? = LaunchSite(),
    @SerializedName("launch_success") var launchSuccess: Boolean? = null,
    @SerializedName("launch_failure_details") var launchFailureDetails: LaunchFailureDetails? = LaunchFailureDetails(),
    @SerializedName("links") var links: LaunchesLinks? = LaunchesLinks(),
    @SerializedName("details") var details: String? = null,
    @SerializedName("static_fire_date_utc") var staticFireDateUtc: String? = null,
    @SerializedName("static_fire_date_unix") var staticFireDateUnix: Int? = null,
    @SerializedName("timeline") var timeline: Timeline? = Timeline(),
    @SerializedName("crew") var crew: String? = null
)

data class Cores(
    @SerializedName("core_serial") var coreSerial: String? = null,
    @SerializedName("flight") var flight: Int? = null,
    @SerializedName("block") var block: String? = null,
    @SerializedName("gridfins") var gridfins: Boolean? = null,
    @SerializedName("legs") var legs: Boolean? = null,
    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("land_success") var landSuccess: String? = null,
    @SerializedName("landing_intent") var landingIntent: Boolean? = null,
    @SerializedName("landing_type") var landingType: String? = null,
    @SerializedName("landing_vehicle") var landingVehicle: String? = null
)

data class FirstStage(
    @SerializedName("cores") var cores: ArrayList<Cores> = arrayListOf()
)

data class OrbitParams(
    @SerializedName("reference_system") var referenceSystem: String? = null,
    @SerializedName("regime") var regime: String? = null,
    @SerializedName("longitude") var longitude: String? = null,
    @SerializedName("semi_major_axis_km") var semiMajorAxisKm: String? = null,
    @SerializedName("eccentricity") var eccentricity: String? = null,
    @SerializedName("periapsis_km") var periapsisKm: Int? = null,
    @SerializedName("apoapsis_km") var apoapsisKm: Int? = null,
    @SerializedName("inclination_deg") var inclinationDeg: Int? = null,
    @SerializedName("period_min") var periodMin: String? = null,
    @SerializedName("lifespan_years") var lifespanYears: String? = null,
    @SerializedName("epoch") var epoch: String? = null,
    @SerializedName("mean_motion") var meanMotion: String? = null,
    @SerializedName("raan") var raan: String? = null,
    @SerializedName("arg_of_pericenter") var argOfPericenter: String? = null,
    @SerializedName("mean_anomaly") var meanAnomaly: String? = null
)

data class Payloads(
    @SerializedName("payload_id") var payloadId: String? = null,
    @SerializedName("norad_id") var noradId: ArrayList<String> = arrayListOf(),
    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("customers") var customers: ArrayList<String> = arrayListOf(),
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("manufacturer") var manufacturer: String? = null,
    @SerializedName("payload_type") var payloadType: String? = null,
    @SerializedName("payload_mass_kg") var payloadMassKg: Int? = null,
    @SerializedName("payload_mass_lbs") var payloadMassLbs: Int? = null,
    @SerializedName("orbit") var orbit: String? = null,
    @SerializedName("orbit_params") var orbitParams: OrbitParams? = OrbitParams()
)

data class SecondStage(
    @SerializedName("block") var block: Int? = null,
    @SerializedName("payloads") var payloads: ArrayList<Payloads> = arrayListOf()
)

data class Fairings(
    @SerializedName("reused") var reused: Boolean? = null,
    @SerializedName("recovery_attempt") var recoveryAttempt: Boolean? = null,
    @SerializedName("recovered") var recovered: Boolean? = null,
    @SerializedName("ship") var ship: String? = null
)

data class Rocket(
    @SerializedName("rocket_id") var rocketId: String? = null,
    @SerializedName("rocket_name") var rocketName: String? = null,
    @SerializedName("rocket_type") var rocketType: String? = null,
    @SerializedName("first_stage") var firstStage: FirstStage? = FirstStage(),
    @SerializedName("second_stage") var secondStage: SecondStage? = SecondStage(),
    @SerializedName("fairings") var fairings: Fairings? = Fairings()
)

data class Telemetry(
    @SerializedName("flight_club") var flightClub: String? = null
)

data class LaunchSite(
    @SerializedName("site_id") var siteId: String? = null,
    @SerializedName("site_name") var siteName: String? = null,
    @SerializedName("site_name_long") var siteNameLong: String? = null
)

data class LaunchFailureDetails(
    @SerializedName("time") var time: Int? = null,
    @SerializedName("altitude") var altitude: String? = null,
    @SerializedName("reason") var reason: String? = null
)

data class LaunchesLinks(
    @SerializedName("mission_patch") var missionPatch: String? = null,
    @SerializedName("mission_patch_small") var missionPatchSmall: String? = null,
    @SerializedName("reddit_campaign") var redditCampaign: String? = null,
    @SerializedName("reddit_launch") var redditLaunch: String? = null,
    @SerializedName("reddit_recovery") var redditRecovery: String? = null,
    @SerializedName("reddit_media") var redditMedia: String? = null,
    @SerializedName("presskit") var presskit: String? = null,
    @SerializedName("article_link") var articleLink: String? = null,
    @SerializedName("wikipedia") var wikipedia: String? = null,
    @SerializedName("video_link") var videoLink: String? = null,
    @SerializedName("youtube_id") var youtubeId: String? = null,
    @SerializedName("flickr_images") var flickrImages: ArrayList<String> = arrayListOf()
)

data class Timeline(
    @SerializedName("webcast_liftoff") var webcastLiftoff: Int? = null
)
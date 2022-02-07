package com.mindera.rocketscience

import com.google.common.truth.Truth
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.data.network.models.LaunchesLinks
import com.mindera.rocketscience.data.network.models.Rocket
import com.mindera.rocketscience.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceXApiTests {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @Test
    fun getAllLaunchesSuccess() = coroutineTestRule.runBlockingTest {
        val expected = listOf(
            LaunchesDto(flightNumber = 1, launchYear = 2000, launchDateUnix = 12234,
                launchSuccess = false, missionName = "", links =
                LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
                rocket = Rocket(rocketName = "", rocketType = "")
            ),
            LaunchesDto(flightNumber = 2, launchYear = 2000, launchDateUnix = 12234,
                launchSuccess = false, missionName = "", links =
                LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
                rocket = Rocket(rocketName = "", rocketType = "")
            )
        )
        val mockApi = mockk<SpaceXApi>()
        coEvery { mockApi.getAlllaunches() } coAnswers { expected }
        val result = mockApi.getAlllaunches()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getCompanyInfoSuccess() = coroutineTestRule.runBlockingTest {
        val expected = CompanyInfoDto(valuation = 1, launchSites = 1,
            employees = 1, founded = 1, founder = "", name = "")
        val mockApi = mockk<SpaceXApi>()
        coEvery { mockApi.getCompanyInfo() } coAnswers { Response.success(expected) }
        val result = mockApi.getCompanyInfo()
        Truth.assertThat(result.isSuccessful).isTrue()
        Truth.assertThat(result.body()).isNotNull()
        Truth.assertThat(result.body()).isEqualTo(expected)
    }
}
package com.mindera.rocketscience

import androidx.paging.*
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.data.network.models.LaunchesLinks
import com.mindera.rocketscience.data.network.models.Rocket
import com.mindera.rocketscience.data.repo.LaunchesMediator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LaunchesMediatorTest {

    private val mockLaunchesDto = listOf(
        LaunchesDto(flightNumber = 1, launchYear = 2000, launchDateUnix = 12234,
            launchSuccess = false, missionName = "", links =
            LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
            rocket = Rocket(rocketName = "", rocketType = "")),
        LaunchesDto(flightNumber = 2, launchYear = 2000, launchDateUnix = 12234,
            launchSuccess = false, missionName = "", links =
            LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
            rocket = Rocket(rocketName = "", rocketType = "")),
        LaunchesDto(flightNumber = 3, launchYear = 2000, launchDateUnix = 12234,
            launchSuccess = false, missionName = "", links =
            LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
            rocket = Rocket(rocketName = "", rocketType = "")),
        LaunchesDto(flightNumber = 4, launchYear = 2000, launchDateUnix = 12234,
            launchSuccess = false, missionName = "", links =
            LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
            rocket = Rocket(rocketName = "", rocketType = "")),
        LaunchesDto(flightNumber = 5, launchYear = 2000, launchDateUnix = 12234,
            launchSuccess = false, missionName = "", links =
            LaunchesLinks(wikipedia = "", missionPatchSmall = "", articleLink = "", videoLink = ""),
            rocket = Rocket(rocketName = "", rocketType = ""))
    )

    private val mockDb = SpaceXDb.invoke(ApplicationProvider.getApplicationContext())
    private val mockApi = mockk<SpaceXApi>()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        coEvery { mockApi.getAlllaunches() } coAnswers { mockLaunchesDto }
        val remoteMediator = LaunchesMediator(mockApi, mockDb)
        val pagingState = PagingState<Int, LaunchesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        Truth.assertThat(result is RemoteMediator.MediatorResult.Success).isTrue()
        Truth.assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        coEvery { mockApi.getAlllaunches() } coAnswers { listOf() }
        val remoteMediator = LaunchesMediator(mockApi, mockDb)
        val pagingState = PagingState<Int, LaunchesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        Truth.assertThat(result is RemoteMediator.MediatorResult.Success).isTrue()
        Truth.assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        coEvery { mockApi.getAlllaunches() } throws IOException()
        val remoteMediator = LaunchesMediator(mockApi, mockDb)
        val pagingState = PagingState<Int, LaunchesEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        Truth.assertThat(result is RemoteMediator.MediatorResult.Error).isTrue()
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}
package com.mindera.rocketscience

import com.google.common.truth.Truth
import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.data.toDomain
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import retrofit2.Response
import com.mindera.rocketscience.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

class SpaceXRepoTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @Test
    fun letCompanyInfoFlowSuccessFromDb() = coroutineTestRule.runBlockingTest {
        val mockApi = mockk<SpaceXApi>()
        val mockDb = mockk<SpaceXDb>()
        val dbCompanyInfo = CompanyInfoEntity(name = "SpaceX", founder = "",
            founded = 1, employees = 1, launchSites = 1, valuation = 1)
        val apiCompanyInfo = CompanyInfoDto(name = "SpaceX", founder = "",
            founded = 1, employees = 1, launchSites = 1, valuation = 1)
        val repo = SpaceXRepo(remoteSource = mockApi, localSource = mockDb)
        coEvery { mockDb.companyInfoDao().getCompanyInfo() } coAnswers { dbCompanyInfo }
        coEvery { mockApi.getCompanyInfo() } coAnswers { Response.success(apiCompanyInfo) }

        val list: ArrayList<Result<CompanyInfo>> = ArrayList()
        val result = repo.letCompanyInfoFlow().collect {
            list.add(it)
        }

        Truth.assertThat(list.size).isEqualTo(2)
        Truth.assertThat(list[0] is Result.Loading).isTrue()
        Truth.assertThat(list[1] is Result.Success).isTrue()
        Truth.assertThat((list[1] as Result.Success).data).isEqualTo(dbCompanyInfo.toDomain())
    }

    @Test
    fun letCompanyInfoFlowSuccessDiffApiFromDb() = coroutineTestRule.runBlockingTest {
        val mockApi = mockk<SpaceXApi>()
        val mockDb = mockk<SpaceXDb>()
        val dbCompanyInfo = CompanyInfoEntity(name = "SpaceX", founder = "",
            founded = 1, employees = 1, launchSites = 1, valuation = 1)
        val apiCompanyInfo = CompanyInfoDto(name = "SpaceX2", founder = "",
            founded = 1, employees = 1, launchSites = 1, valuation = 1)
        val repo = SpaceXRepo(remoteSource = mockApi, localSource = mockDb)
        coEvery { mockDb.companyInfoDao().getCompanyInfo() } coAnswers { dbCompanyInfo }
        coEvery { mockDb.companyInfoDao().insert(any()) } returns Unit
        coEvery { mockApi.getCompanyInfo() } coAnswers { Response.success(apiCompanyInfo) }

        val list: ArrayList<Result<CompanyInfo>> = ArrayList()
        val result = repo.letCompanyInfoFlow().collect {
            list.add(it)
        }

        Truth.assertThat(list.size).isEqualTo(3)
        Truth.assertThat(list[0] is Result.Loading).isTrue()
        Truth.assertThat(list[1] is Result.Success).isTrue()
        Truth.assertThat(list[2] is Result.Success).isTrue()
        Truth.assertThat((list[2] as Result.Success).data).isEqualTo(apiCompanyInfo.toDomain())
    }

    @Test
    fun letCompanyInfoFlowSuccessFromApiNullDb() = coroutineTestRule.runBlockingTest {
        val mockApi = mockk<SpaceXApi>()
        val mockDb = mockk<SpaceXDb>()
        val apiCompanyInfo = CompanyInfoDto(name = "SpaceX2", founder = "",
            founded = 1, employees = 1, launchSites = 1, valuation = 1)
        val repo = SpaceXRepo(remoteSource = mockApi, localSource = mockDb)
        coEvery { mockDb.companyInfoDao().getCompanyInfo() } coAnswers { null }
        coEvery { mockDb.companyInfoDao().insert(any()) } returns Unit
        coEvery { mockApi.getCompanyInfo() } coAnswers { Response.success(apiCompanyInfo) }

        val list: ArrayList<Result<CompanyInfo>> = ArrayList()
        val result = repo.letCompanyInfoFlow().collect {
            list.add(it)
        }

        Truth.assertThat(list.size).isEqualTo(2)
        Truth.assertThat(list[0] is Result.Loading).isTrue()
        Truth.assertThat(list[1] is Result.Success).isTrue()
        Truth.assertThat((list[1] as Result.Success).data).isEqualTo(apiCompanyInfo.toDomain())
    }

    @Test
    fun filterQueryTests() = coroutineTestRule.runBlockingTest {
        val mockApi = mockk<SpaceXApi>()
        val mockDb = mockk<SpaceXDb>()
        val repo = SpaceXRepo(remoteSource = mockApi, localSource = mockDb)

        var expectedQuery = "SELECT * FROM launches ORDER BY missionName ASC"
        var query = repo.filterQuery(FilterData(sorting = Sort.ASC, filterByDate = false, launchSuccessFilter = LaunchSuccessFilter.ALL, maxYear = 2005, minYear = 2000))
        Truth.assertThat(query.sql).isEqualTo(expectedQuery)

        expectedQuery = "SELECT * FROM launches WHERE launchSuccess = 1 ORDER BY missionName ASC"
        query = repo.filterQuery(FilterData(sorting = Sort.ASC, filterByDate = false, launchSuccessFilter = LaunchSuccessFilter.SUCCESS, maxYear = 2005, minYear = 2000))
        Truth.assertThat(query.sql).isEqualTo(expectedQuery)

        expectedQuery = "SELECT * FROM launches WHERE launchSuccess = 1 AND launchYear >= 2000 AND launchYear <= 2005 ORDER BY missionName ASC"
        query = repo.filterQuery(FilterData(sorting = Sort.ASC, filterByDate = true, launchSuccessFilter = LaunchSuccessFilter.SUCCESS, maxYear = 2005, minYear = 2000))
        Truth.assertThat(query.sql).isEqualTo(expectedQuery)

        expectedQuery = "SELECT * FROM launches WHERE launchYear >= 2000 AND launchYear <= 2005 ORDER BY missionName ASC"
        query = repo.filterQuery(FilterData(sorting = Sort.ASC, filterByDate = true, launchSuccessFilter = LaunchSuccessFilter.ALL, maxYear = 2005, minYear = 2000))
        Truth.assertThat(query.sql).isEqualTo(expectedQuery)
    }
}
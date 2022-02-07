package com.mindera.rocketscience

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.ui.spacex.SpaceXViewModel
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.ui.utils.toJson
import com.mindera.rocketscience.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import retrofit2.Response
import com.mindera.rocketscience.utils.Result
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

class SpaceXViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @Test
    fun getCompanyInfoAndFilterDataSuccess() = coroutineTestRule.runBlockingTest {
        val expected = CompanyInfo(name = "SpaceX", valuation = 1234,
            launchSites = 1, employees = 1, founded = 1, founder = "")
        val expectedFilterData = FilterData(minYear = 2005, maxYear = 2020, launchSuccessFilter = LaunchSuccessFilter.SUCCESS, filterByDate = false, sorting = Sort.ASC)
        val savedStateHandle = SavedStateHandle().apply {
            set(FILTER_KEY, expectedFilterData.toJson())
        }

        val repo = mockk<SpaceXRepo>()
        every { repo.letCompanyInfoFlow() } returns flowOf(Result.success(data = expected))
        val viewModel = SpaceXViewModel(savedStateHandle, repo)
        val valueCompanyInfo = viewModel.companyInfo.first()
        val valueFilter = viewModel.filterData.first()
        Truth.assertThat(valueCompanyInfo).isInstanceOf(Result.Success::class.java)
        Truth.assertThat((valueCompanyInfo as Result.Success).data).isEqualTo(expected)
        Truth.assertThat(valueFilter).isEqualTo(expectedFilterData)
    }
}
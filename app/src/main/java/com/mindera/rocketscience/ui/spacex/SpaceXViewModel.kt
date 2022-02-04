package com.mindera.rocketscience.ui.spacex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mindera.rocketscience.LAUNCH_SUCCESS_KEY
import com.mindera.rocketscience.ORDER_KEY
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Launches
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilterState(
    val sorting: Sort = Sort.ASC,
    val launchSuccessFilter: LaunchSuccessFilter = LaunchSuccessFilter.ALL
)

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SpaceXViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
                                          private val repo: SpaceXRepo): ViewModel() {

    private val _filterState: MutableStateFlow<FilterState> = MutableStateFlow(FilterState(
        sorting = Sort.valueOf(savedStateHandle.get(ORDER_KEY) ?: "ASC"),
        launchSuccessFilter = LaunchSuccessFilter.valueOf(savedStateHandle.get(LAUNCH_SUCCESS_KEY) ?: "ALL"))
    )
    val filterState: StateFlow<FilterState>
        get() = _filterState

    private val _companyInfo = MutableStateFlow<Result<CompanyInfo>>(Result.loading())
    val companyInfo: StateFlow<Result<CompanyInfo>>
        get() = _companyInfo

    private val _lstLaunches = filterState.transformLatest {
        state -> emitAll(repo.letLaunchesFlow(state.sorting, state.launchSuccessFilter).cachedIn(viewModelScope))
    }

    val lstLaunches: Flow<PagingData<Launches>>
        get() = _lstLaunches

    init {
        onGetCompanyInfo()
    }

    fun onGetCompanyInfo() {
        viewModelScope.launch {
            repo.letCompanyInfoFlow()
                .flowOn(Dispatchers.IO)
                .collect { info ->
                    _companyInfo.value = info
                }
        }
    }
}
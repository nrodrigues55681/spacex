package com.mindera.rocketscience.ui.spacex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mindera.rocketscience.*
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Launches
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.ui.utils.fromJson
import com.mindera.rocketscience.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SpaceXViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
                                          private val repo: SpaceXRepo): ViewModel() {

    private val _filterData: MutableStateFlow<FilterData> = MutableStateFlow(
        savedStateHandle.get<String>(FILTER_KEY)?.fromJson() ?: FilterData())

    val filterData: StateFlow<FilterData>
        get() = _filterData

    private val _companyInfo = MutableStateFlow<Result<CompanyInfo>>(Result.loading())
    val companyInfo: StateFlow<Result<CompanyInfo>>
        get() = _companyInfo

    private val _lstLaunches = filterData.transformLatest {
        filterData -> emitAll(repo.letLaunchesFlow(filterData).cachedIn(viewModelScope))
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
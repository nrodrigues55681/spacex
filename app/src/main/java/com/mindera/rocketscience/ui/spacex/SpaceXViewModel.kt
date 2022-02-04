package com.mindera.rocketscience.ui.spacex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.Launches
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val repo: SpaceXRepo): ViewModel() {
    private val sorting: MutableStateFlow<Sort> = MutableStateFlow(Sort.ASC)

    private val _companyInfo = MutableStateFlow<Result<CompanyInfo>>(Result.loading())
    val companyInfo: StateFlow<Result<CompanyInfo>>
        get() = _companyInfo

    private val _lstLaunches = sorting.transformLatest {
        sort -> emitAll(repo.letLaunchesFlow(sort).cachedIn(viewModelScope))
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

    fun onSortingSelected(sorting: Sort) {
        this.sorting.value = sorting
    }
}
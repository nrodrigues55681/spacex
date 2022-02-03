package com.mindera.rocketscience.ui.spacex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.data.repo.SpaceXRepo
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val repo: SpaceXRepo): ViewModel() {

    private val _companyInfo = MutableStateFlow<Result<CompanyInfo>>(Result.loading())
    val companyInfo: StateFlow<Result<CompanyInfo>>
        get() = _companyInfo

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
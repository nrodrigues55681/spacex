package com.mindera.rocketscience.ui.spacex.dialogs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.*
import com.mindera.rocketscience.data.repo.FilterDialogRepo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.ui.utils.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilterDialogUiState(
    val filterData: FilterData,
    val sliderMaxYear: Int,
    val sliderMinYear: Int,
    val lstSort: List<Sort> = enumValues<Sort>().map { it },
    val lstLaunchSuccessFilter: List<LaunchSuccessFilter> = enumValues<LaunchSuccessFilter>().map { it },
)

@HiltViewModel
class FilterDialogViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
                                                repo: FilterDialogRepo): ViewModel() {
    private val _uiState: MutableStateFlow<FilterDialogUiState> = MutableStateFlow(FilterDialogUiState(
        filterData = savedStateHandle.get<String>(FILTER_KEY)?.fromJson() ?: FilterData(),
        sliderMinYear = 0,
        sliderMaxYear = 0)
    )

    val uiState: StateFlow<FilterDialogUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            repo.letMaxMinLaunch().flowOn(Dispatchers.IO).collect {
                val maxYear = if(uiState.value.filterData.maxYear == 0)
                    it.launchYearMax else uiState.value.filterData.maxYear

                val minYear = if(uiState.value.filterData.minYear == 0)
                    it.launchYearMin else uiState.value.filterData.minYear

                _uiState.value = uiState.value.copy(sliderMaxYear = it.launchYearMin, sliderMinYear = it.launchYearMax,
                    filterData = uiState.value.filterData.copy(maxYear = maxYear, minYear = minYear))
            }
        }
    }

    fun onSortingSelected(position: Int){
        _uiState.value = uiState.value.copy(
            filterData = uiState.value.filterData
                .copy(sorting = uiState.value.lstSort[position]))
    }

    fun onLaunchSuccessFilterSelected(position: Int){
        _uiState.value = uiState.value.copy(
            filterData = uiState.value.filterData
                .copy(launchSuccessFilter = uiState.value.lstLaunchSuccessFilter[position]))
    }

    fun onChangeYearFilter(minYear: Int, maxYear:Int){
        _uiState.value = uiState.value
            .copy(filterData = uiState.value.filterData
                .copy(minYear = minYear, maxYear = maxYear))
    }

    fun onEnableYearFilter(filterByDate: Boolean){
        _uiState.value = uiState.value.copy(filterData = uiState.value.filterData.copy(filterByDate = filterByDate))
    }
}
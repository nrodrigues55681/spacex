package com.mindera.rocketscience.ui.spacex.dialogs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mindera.rocketscience.LAUNCH_SUCCESS_KEY
import com.mindera.rocketscience.ORDER_KEY
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class FilterDialogUiState(
    val sorting: Sort = Sort.ASC,
    val launchSuccessFilter: LaunchSuccessFilter = LaunchSuccessFilter.ALL,
    val lstSort: List<Sort> = enumValues<Sort>().map { it },
    val lstLaunchSuccessFilter: List<LaunchSuccessFilter> = enumValues<LaunchSuccessFilter>().map { it }
)

@HiltViewModel
class FilterDialogViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {
    private val _uiState: MutableStateFlow<FilterDialogUiState> = MutableStateFlow(FilterDialogUiState(
        sorting = Sort.valueOf(savedStateHandle.get(ORDER_KEY) ?: "ASC"),
        launchSuccessFilter = LaunchSuccessFilter.valueOf(savedStateHandle.get(LAUNCH_SUCCESS_KEY) ?: "ALL")
    ))

    val uiState: StateFlow<FilterDialogUiState>
        get() = _uiState

    fun onSortingSelected(position: Int){
        _uiState.value = uiState.value.copy(sorting = uiState.value.lstSort[position])
    }

    fun onLaunchSuccessFilterSelected(position: Int){
        _uiState.value = uiState.value.copy(launchSuccessFilter = uiState.value.lstLaunchSuccessFilter[position])
    }
}
package com.mindera.rocketscience.ui.spacex.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mindera.rocketscience.R
import com.mindera.rocketscience.ui.components.GroupRadioButton
import com.mindera.rocketscience.ui.components.OkCancelButton
import com.mindera.rocketscience.ui.components.YearFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDialog(viewModel: FilterDialogViewModel,
                 onCancelClick: () -> Unit,
                 onOkClick: () -> Unit){

    val uiState by viewModel.uiState.collectAsState()

    Dialog(onDismissRequest = onCancelClick) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.margin_default))
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))) {
                    YearFilter(
                        modifier = Modifier.fillMaxWidth(),
                        enable = uiState.filterData.filterByDate,
                        minYear = uiState.filterData.minYear,
                        maxYear = uiState.filterData.maxYear,
                        defaultMaxYear = uiState.sliderMinYear,
                        defaultMinYear = uiState.sliderMaxYear,
                        onEnableFilter = viewModel::onEnableYearFilter,
                        onValueChange = viewModel::onChangeYearFilter
                    )

                    GroupRadioButton(modifier = Modifier.fillMaxWidth(),
                        onRadioButtonClick = viewModel::onSortingSelected,
                        title = stringResource(id = R.string.order_by),
                        itemSelected = uiState.filterData.sorting,
                        items = uiState.lstSort)

                    GroupRadioButton(modifier = Modifier.fillMaxWidth(),
                        onRadioButtonClick = viewModel::onLaunchSuccessFilterSelected,
                        title = stringResource(id = R.string.launch_success),
                        itemSelected = uiState.filterData.launchSuccessFilter,
                        items = uiState.lstLaunchSuccessFilter)

                    OkCancelButton(modifier = Modifier.fillMaxWidth(),
                        cancelTitle = stringResource(id = R.string.cancel),
                        okTitle = stringResource(id = R.string.ok),
                        onCancelClick = onCancelClick,
                        onOkClick = onOkClick
                    )
                }
            }
        }
    }
}
package com.mindera.rocketscience.ui.spacex


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.mindera.rocketscience.R
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.ui.components.*
import com.mindera.rocketscience.utils.Result
import java.text.MessageFormat

@Composable
fun SpaceXScreen(viewModel: SpaceXViewModel) {
    Scaffold(
        topBar = { TopBar(title = stringResource(id = R.string.app_name), showBackButton = false) }
    ) {
        SpaceXContent(viewModel = viewModel)
    }
}

@Composable
fun SpaceXContent(viewModel: SpaceXViewModel){
    Column(modifier = Modifier.fillMaxSize()) {
        BarWithTitle(title = stringResource(id = R.string.company))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2half)))
        CompanyInfoItem(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2half)))
        BarWithTitle(title = stringResource(id = R.string.launches))
    }
}

@Composable
fun CompanyInfoItem(viewModel: SpaceXViewModel){
    val companyInfo by viewModel.companyInfo.collectAsState()
    when(companyInfo){
        is Result.Error -> ErrorView(message = stringResource(id = R.string.something_went_wrong),
            buttonTitle = stringResource(id = R.string.try_again), onClick = viewModel::onGetCompanyInfo)
        Result.Loading -> LoadingItem()
        is Result.Success -> {
            val companyInfo = (companyInfo as Result.Success<CompanyInfo>)
            val companyInfoText = MessageFormat.format(stringResource(id = R.string.company_info),
                companyInfo.data.name,
                companyInfo.data.founder,
                companyInfo.data.founded,
                companyInfo.data.employees,
                companyInfo.data.launchSites,
                companyInfo.data.valuation)

            Text(text = companyInfoText,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.margin_default),
                    end = dimensionResource(id = R.dimen.margin_default
            )))
        }
    }
}
package com.mindera.rocketscience.ui.spacex


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.mindera.rocketscience.R
import com.mindera.rocketscience.data.convertToDateAndTime
import com.mindera.rocketscience.data.daysFromSince
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.Launches
import com.mindera.rocketscience.ui.components.*
import com.mindera.rocketscience.ui.theme.Purple700
import com.mindera.rocketscience.ui.utils.LinksData
import com.mindera.rocketscience.utils.Result
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.MessageFormat


@Composable
fun SpaceXScreen(viewModel: SpaceXViewModel,
                 onShowFilterDialog: () -> Unit,
                 onShowLinkDialog: (LinksData) -> Unit) {
    Scaffold(
        topBar = { TopBar(title = stringResource(id = R.string.app_name), onFilterClick = onShowFilterDialog) }
    ) {
        SpaceXContent(viewModel = viewModel, onShowLinkDialog = onShowLinkDialog)
    }
}

@Composable
fun SpaceXContent(viewModel: SpaceXViewModel,
                  onShowLinkDialog: (LinksData) -> Unit){
    Column(modifier = Modifier.fillMaxSize()) {
        BarWithTitle(title = stringResource(id = R.string.company))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2half)))
        CompanyInfo(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2half)))
        BarWithTitle(title = stringResource(id = R.string.launches))
        LaunchesInfo(viewModel = viewModel, onShowLinkDialog = onShowLinkDialog)
    }
}

@Composable
fun CompanyInfo(viewModel: SpaceXViewModel){
    val companyInfoResult by viewModel.companyInfo.collectAsState()
    when(companyInfoResult){
        is Result.Error -> ErrorView(message = stringResource(id = R.string.something_went_wrong),
            buttonTitle = stringResource(id = R.string.try_again), onClick = viewModel::onGetCompanyInfo)
        Result.Loading -> LoadingItem()
        is Result.Success -> {
            val companyInfo = (companyInfoResult as Result.Success<CompanyInfo>)
            val companyInfoText = MessageFormat.format(stringResource(id = R.string.company_info),
                companyInfo.data.name,
                companyInfo.data.founder,
                companyInfo.data.founded,
                companyInfo.data.employees,
                companyInfo.data.launchSites,
                companyInfo.data.valuation)

            Text(text = companyInfoText,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.margin_default),
                    end = dimensionResource(id = R.dimen.margin_default
            )))
        }
    }
}

@Composable
fun LaunchesInfo(viewModel: SpaceXViewModel, onShowLinkDialog: (LinksData) -> Unit){
    val lstLaunches = viewModel.lstLaunches.collectAsLazyPagingItems()
    LazyColumn {
        items(lstLaunches) { launch ->
            launch?.let {
                LaunchItem(launch = launch, onShowLinkDialog = onShowLinkDialog)
            }
        }

        lstLaunches.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        ErrorView(
                            message = stringResource(id = R.string.something_went_wrong),
                            buttonTitle = stringResource(id = R.string.try_again),
                            modifier = Modifier.fillParentMaxSize(),
                            onClick = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorView(
                            message = stringResource(id = R.string.something_went_wrong),
                            buttonTitle = stringResource(id = R.string.try_again),
                            onClick = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LaunchItem(launch: Launches, onShowLinkDialog: (LinksData) -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onShowLinkDialog(LinksData(
            articleLink = URLEncoder.encode(launch.articleLink, StandardCharsets.UTF_8.toString()),
            videoLink = URLEncoder.encode(launch.videoLink, StandardCharsets.UTF_8.toString()),
            wikipediaLink = URLEncoder.encode(launch.wikipedia, StandardCharsets.UTF_8.toString()))) }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.margin_default),
                    vertical = dimensionResource(
                        id = R.dimen.margin_half
                    )
                )
        ) {
            val (image, mission, dateTime, rocket, days, launchSuccess) = createRefs()
            val marginDefault = dimensionResource(id = R.dimen.margin_default)
            val margin2Half = dimensionResource(id = R.dimen.margin_2half)

            LaunchImage(modifier = Modifier.constrainAs(image){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }, url = launch.missionPatchSmall)
            Text2Style(
                modifier = Modifier.constrainAs(mission){
                    start.linkTo(image.end, marginDefault)
                    end.linkTo(launchSuccess.start, margin2Half)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = "${stringResource(id = R.string.mission)}: ",
                description = launch.missionName)

            val (date, time) = launch.launchDateUnix.convertToDateAndTime()
            Text2Style(
                modifier = Modifier.constrainAs(dateTime){
                    start.linkTo(image.end, marginDefault)
                    end.linkTo(launchSuccess.start, margin2Half)
                    top.linkTo(mission.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = "${stringResource(id = R.string.datetime)}: ",
                description = "$date ${stringResource(id = R.string.at)} $time")

            Text2Style(
                modifier = Modifier.constrainAs(rocket){
                    start.linkTo(image.end, marginDefault)
                    end.linkTo(parent.end, margin2Half)
                    top.linkTo(dateTime.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = "${stringResource(id = R.string.rocket)}: ",
                description = "${launch.rocketName} / ${launch.rocketType}")

            val daysValue = launch.launchDateUnix.daysFromSince()
            val daysTitle = if(daysValue > 0) stringResource(id = R.string.days_since) else stringResource(id = R.string.days_from)

            Text2Style(
                modifier = Modifier.constrainAs(days){
                    start.linkTo(image.end, marginDefault)
                    end.linkTo(launchSuccess.start, margin2Half)
                    top.linkTo(rocket.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = "$daysTitle: ",
                description = "$daysValue")

            Icon(
                modifier = Modifier.constrainAs(launchSuccess){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                imageVector = if(launch.launchSuccess) Icons.Default.Check else Icons.Default.Clear,
                contentDescription = "",
                tint = Purple700
            )

        }
        Divider(color = Color.LightGray)
    }

}

@Composable
fun LaunchImage(
    modifier: Modifier = Modifier,
    url: String){
    Image(
        modifier = modifier.size(dimensionResource(id = R.dimen.icon_default)),
        painter = rememberImagePainter(url),
        contentDescription = null,
    )
}
package com.mindera.rocketscience.ui.spacex


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mindera.rocketscience.R
import com.mindera.rocketscience.ui.components.TopBar

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
}
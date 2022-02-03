package com.mindera.rocketscience

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mindera.rocketscience.ui.spacex.SpaceXScreen
import com.mindera.rocketscience.ui.spacex.SpaceXViewModel

internal sealed class Screen(val route: String) {
    object SpaceX: Screen("spacex")
}

@Composable
fun SpaceXNavApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.SpaceX.route) {
        composable(route = Screen.SpaceX.route) {
            val viewModel = hiltViewModel<SpaceXViewModel>()
            SpaceXScreen(viewModel = viewModel)
        }
    }
}
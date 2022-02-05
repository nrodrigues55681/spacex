package com.mindera.rocketscience

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mindera.rocketscience.ui.spacex.SpaceXScreen
import com.mindera.rocketscience.ui.spacex.SpaceXViewModel
import com.mindera.rocketscience.ui.spacex.dialogs.FilterDialog
import com.mindera.rocketscience.ui.spacex.dialogs.FilterDialogViewModel
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.ui.utils.toJson

const val FILTER_KEY = "filter_data"

internal sealed class Screen(val route: String) {
    object SpaceX: Screen("spacex/{$FILTER_KEY}") {
        fun createRoute(filterData: FilterData)
                = "spacex/${filterData.toJson()}"
    }
}

internal sealed class Dialog(val route: String) {
    object Filter: Dialog("filter/{$FILTER_KEY}") {
        fun createRoute(filterData: FilterData)
            = "filter/${filterData.toJson()}"
    }
}

@Composable
fun SpaceXNavApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.SpaceX.route) {
        composable(route = Screen.SpaceX.route,
            arguments = listOf(
                navArgument(FILTER_KEY) {
                    type = NavType.StringType
                    defaultValue = FilterData().toJson()
                })
        ){
            val viewModel = hiltViewModel<SpaceXViewModel>()
            SpaceXScreen(viewModel = viewModel,
                onShowFilterDialog = {
                    navController.navigate(Dialog.Filter.createRoute(viewModel.filterData.value))
                })
        }

        dialog(route = Dialog.Filter.route,
            arguments = listOf(
                navArgument(FILTER_KEY) {
                    type = NavType.StringType
                    defaultValue = FilterData().toJson()
                })
        ){
            val viewModel = hiltViewModel<FilterDialogViewModel>()
            FilterDialog(viewModel = viewModel,
                onOkClick = {
                    val spaceXRoute = Screen.SpaceX.createRoute(viewModel.uiState.value.filterData)
                    navController.navigate(spaceXRoute){
                        popUpTo(spaceXRoute) { inclusive = true }
                    }
                },
                onCancelClick = { navController.navigateUp() })
        }
    }
}
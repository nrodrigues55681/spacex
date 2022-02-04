package com.mindera.rocketscience

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Sort
import com.mindera.rocketscience.ui.spacex.SpaceXScreen
import com.mindera.rocketscience.ui.spacex.SpaceXViewModel
import com.mindera.rocketscience.ui.spacex.dialogs.FilterDialog
import com.mindera.rocketscience.ui.spacex.dialogs.FilterDialogViewModel

const val ORDER_KEY = "order_by"
const val LAUNCH_SUCCESS_KEY = "launch_success"

internal sealed class Screen(val route: String) {
    object SpaceX: Screen("spacex/{${ORDER_KEY}}/{${LAUNCH_SUCCESS_KEY}}"){
        fun createRoute(sort: Sort, launchSuccessFilter: LaunchSuccessFilter)
                = "spacex/${sort.name}/${launchSuccessFilter.name}"
    }
}

internal sealed class Dialog(val route: String) {
    object Filter: Dialog("filter/{${ORDER_KEY}}/{${LAUNCH_SUCCESS_KEY}}") {
        fun createRoute(sort: Sort, launchSuccessFilter: LaunchSuccessFilter)
            = "filter/${sort.name}/${launchSuccessFilter.name}"
    }
}

@Composable
fun SpaceXNavApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.SpaceX.route) {
        composable(route = Screen.SpaceX.route,
            arguments = listOf(
                navArgument(ORDER_KEY) {
                    type = NavType.StringType
                    defaultValue = Sort.ASC.name
                },
                navArgument(LAUNCH_SUCCESS_KEY) {
                    type = NavType.StringType
                    defaultValue = LaunchSuccessFilter.ALL.name
                })
        ){
            val viewModel = hiltViewModel<SpaceXViewModel>()
            SpaceXScreen(viewModel = viewModel,
                onShowFilterDialog = {
                    navController.navigate(Dialog.Filter.createRoute(
                        sort = viewModel.filterState.value.sorting,
                        launchSuccessFilter = viewModel.filterState.value.launchSuccessFilter)
                    )
                })
        }

        dialog(route = Dialog.Filter.route,
            arguments = listOf(
                navArgument(ORDER_KEY) {
                    type = NavType.StringType
                    defaultValue = Sort.ASC.name
                },
                navArgument(LAUNCH_SUCCESS_KEY) {
                    type = NavType.StringType
                    defaultValue = LaunchSuccessFilter.ALL.name
                })
        ){
            val viewModel = hiltViewModel<FilterDialogViewModel>()
            FilterDialog(viewModel = viewModel,
                onOkClick = {
                    val spaceXRoute = Screen.SpaceX.createRoute(
                        sort = viewModel.uiState.value.sorting,
                        launchSuccessFilter = viewModel.uiState.value.launchSuccessFilter)

                    navController.navigate(spaceXRoute){
                        popUpTo(spaceXRoute) { inclusive = true }
                    }
                },
                onCancelClick = { navController.navigateUp() })
        }
    }
}
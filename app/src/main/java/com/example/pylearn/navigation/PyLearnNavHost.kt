package com.example.pylearn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pylearn.ui.activity.ActivityScreen
import com.example.pylearn.ui.landing.LandingScreen
import com.example.pylearn.ui.settings.SettingsScreen
import com.example.pylearn.ui.statistics.StatisticsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.example.pylearn.ui.landing.LandingViewModel
/**
 * Contains the main navigation graph for PyLearn.
 */
@Composable
fun PyLearnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Landing.route,
        modifier = modifier
    ) {
        composable(route = AppDestination.Landing.route) {
            val landingViewModel: LandingViewModel = viewModel()
            val uiState by landingViewModel.uiState.collectAsStateWithLifecycle()

            LandingScreen(
                uiState = uiState,
                onTopicClick = {
                    navController.navigate(AppDestination.Activity.route)
                },
                onStatisticsClick = {
                    navController.navigate(AppDestination.Statistics.route)
                },
                onSettingsClick = {
                    navController.navigate(AppDestination.Settings.route)
                }
            )
        }

        composable(route = AppDestination.Activity.route) {
            ActivityScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.Statistics.route) {
            StatisticsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AppDestination.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}